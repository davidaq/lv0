
package actions;


public class Home extends BaseAction {
    
    public static class TabItem {
        public String title, icon, url;
        TabItem(String title, String icon, String url) {
            this.title = title;
            this.icon = icon;
            this. url = url;
        }
    }
    
    public String tabs() {
        TabItem[] items = new TabItem[] {
            new TabItem("关注焦点", "star", "home"),
            new TabItem("新鲜记忆", "flag", "feed"),
            new TabItem("媒体影集", "film", "album"),
            new TabItem("旅游计划", "check", "plan"),
            new TabItem("景点百科", "share", "wiki")
        };
        return jsonResult(items);
    }
    
    public static class BackgroundItem {
        public String title, url;
        BackgroundItem(String title, String url) {
            this.title = title;
            this. url = url;
        }
    }
    
    public String backgrounds() {
        BackgroundItem[] items = new BackgroundItem[] {
            new BackgroundItem("夜幕沙丘", "static/images/twilight_dune.jpg"),
            new BackgroundItem("绿色田野", "static/images/green_meadow.jpg"),
            new BackgroundItem("壁立千仞", "static/images/clifs.jpg"),
            new BackgroundItem("木质风情", "static/images/wood.jpg")
        };
        return jsonResult(items);
    }
}
