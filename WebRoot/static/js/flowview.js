function FlowView(fv) {
	if(!$(fv).hasClass('.flowview'))
		fv = $('.flowview:first', fv);
	var wrap = document.createElement('div');
	wrap.className = 'flowview-wrap';
	$(fv).wrap(wrap);
	this.queue = [];
	var present = $('.block', fv);
	$(fv).html('');
	var me = this;
	this.fv = fv;
	this.slot = false;
	this.xDispos = 0;
	var pos = 0;
	present.each(function() {
		me.addBlock($(this).html(), $(this).hasClass('big'));
	});
	$(fv).mousewheel(function(event, delta) {
		event.preventDefault();
		if(delta > 0) {	// scroll to front
			pos += 70;
			if(pos > 0)
				pos = 0;
		} else {	// scroll to tail
			pos -= 70;
			try {
				if(me.xDispos < $(fv).width()) {
					pos = 0;
				} else if(pos < $(fv).width() - me.xDispos - 20) {
					pos = $(fv).width() - me.xDispos - 20;
				} else {
					throw 0;
				}
				me.load();
			} catch(x){}
		}
		$(fv).stop().animate({left: pos + 'px'}, 200);
    });
}
FlowView.prototype.load = function(listener) {
	if(listener) {
		this.loadMore = listener;
	} else if(this.loadMore) {
		if(this.loadDelay) {
			return;
		}
		var me = this;
		this.loadDelay = setTimeout(function() {
			me.loadDelay = false;
		}, 1000);
		this.loadMore();
	}
}
FlowView.prototype.addBlock = function(html, big) {
	var item = document.createElement('div');
	item.innerHTML = html;
	item.className = 'block' + (big ? ' big':'');
	
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
	$(item).hide();
	this.queue.push(item);
	return item;
};
FlowView.prototype.show = function(selfcall) {
	if(!selfcall && this.showing)
		return;
	this.load();
	this.showing = false;
	if(this.queue.length > 0) {
		this.showing = true;
		var item = this.queue.shift();
		$(item).fadeIn(300);
		var me = this;
		setTimeout(function() {
			me.show(true);
		}, 50);
	}
};
