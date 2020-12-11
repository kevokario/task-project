angular.module("homer").factory("appFactory", function ($http, utilityService) {
    var base_url = utilityService.getBaseUrl();
    var factory = {};

    factory.getAllInstitutions = function () {
        var k = $http.get(base_url + 'api/institution', {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    factory.getIndexInformation = function () {
        var k = $http.get(base_url + 'api', {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    factory.getInstitutionById = function (institutionid) {
        var url = "api/institution/" + institutionid;
        var k = $http.get(base_url + url, {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    }

    factory.getInstitutionCourses = function (institutionid) {
//        var url = "api/content/courses/" + institutionid;
        var url = "api/institution/" + institutionid + "/courses";
        var k = $http.get(base_url + url, {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    factory.getInstitutionStudents = function (institutionid) {
        var url = "api/institution/" + institutionid + "/students";
        var k = $http.get(base_url + url, {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    factory.getStudentById = function (institutionid, studentid) {
        var url = "api/institution/" + institutionid + "/students/" + studentid;
        var k = $http.get(base_url + url, {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    factory.getCourseById = function (institutionid, courseid) {
        var url = "api/institution/" + institutionid + "/courses/" + courseid;
        var k = $http.get(base_url + url, {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    factory.getAllCourses = function () {
        var url = "api/course/";
        var k = $http.get(base_url + url, {
        }).then(function (resp) {
            return resp.data;
        }).catch(function (resp) {
            return undefined;
        });
        return k;
    };

    return factory;

});