
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
        TabItem[] items;
        if(session("myUserinfo") == null){
            items = new TabItem[] {
                    new TabItem("%{Hot_Focus}", "star", "home"),
                    new TabItem("%{Login}", "user", "login"),
                    new TabItem("%{Register}", "bell", "register")
                };
        } else {
            items = new TabItem[] {
                new TabItem("%{Hot_Focus}", "star", "home"),
                new TabItem("%{My_Feed}", "flag", "feed"),
                new TabItem("%{Album}", "film", "album"),
                new TabItem("%{Travel_Plan}", "check", "plan"),
                new TabItem("%{Resort_Wiki}", "share", "wiki")
            };
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
