function FlowView(fv) {
	if(!$(fv).hasClass('.flowview'))
		fv = $('.flowview:first', fv);
	var wrap = document.createElement('div');
	wrap.className = 'flowview-wrap';
	$(fv).wrap(wrap);
	this.queue = $('.block', fv).toArray();
	$('.block', fv).remove();
	this.fv = fv;
	this.slot = false;
	this.xDispos = 0;
	var pos = 0;
	var me = this;
	$(fv).mousewheel(function(event, delta) {
		event.preventDefault();
		if(delta > 0) {	// scroll to front
			pos += 70;
		} else {	// scroll to tail
			pos -= 70;
		}
		if(pos > 0 || me.xDispos < $(fv).width())
			pos = 0;
		else if(pos < $(fv).width() - me.xDispos - 20)
			pos = $(fv).width() - me.xDispos - 20;
		$(fv).stop().animate({left: pos + 'px'}, 200);
    });
}
FlowView.prototype.addBlock = function(html, big) {
	var item = document.createElement('div');
	item.className = 'block' + (big ? ' big':'');
	this.queue.push(item);
};
FlowView.prototype.show = function() {
	if(this.queue.length > 0) {
		var item = this.queue.shift();
		var me = this;
		function newCol() {
			var col = document.createElement('div');
			col.className = 'column';
			$(col).css('left', me.xDispos);
			$(me.fv).append(col);
			me.xDispos += 318;
			return col;
		}
		if($(item).hasClass('big')) {
			$(newCol()).append(item);
		} else if(this.slot) {
			$(this.slot).append(item);
			this.slot = false;
		} else {
			var col = newCol();
			$(col).append(item);
			this.slot = col;
		}
		$(item).fadeOut(0).fadeIn(300);
		setTimeout(function() {
			me.show();
		}, 50);
	}
};
