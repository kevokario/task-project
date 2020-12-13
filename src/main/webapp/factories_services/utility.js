angular.module("homer").service("utilityService", function (notify) {
    var base_url = "";

    this.getBaseUrl = function () {
        return base_url;
    };

    this.showNotification = function (text, notify_class) {
        notify.closeAll();

        notify({
            message: text,
            classes: notify_class,
            templateUrl: "views/notification/notify.html"
        });
    };

    this.notifyInfo = function (text) {
        this.showNotification(text, "alert-info");
    };
    this.notifySuccess = function (text) {
        var notify_class = "alert-success";
        this.showNotification(text, notify_class);
    };
    this.notifyWarning = function (text) {
        var notify_class = "alert-warning";
        this.showNotification(text, notify_class);
    };
    this.notifyDanger = function (text) {
        var notify_class = "alert-danger";
        this.showNotification(text, notify_class);
    };
    this.networkError = function(){
        var notify_class = "alert-danger";
        var text = "Network Error! Please check your internet connection then try again, notify Admin if this error persists!";
        this.showNotification(text, notify_class);
    };
});
