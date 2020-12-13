function configState($stateProvider, $urlRouterProvider, $httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $urlRouterProvider.when("/", "/h", "**").otherwise("/h/home");
    $stateProvider
            .state("home", {
                url: "/h",
                templateUrl: "./views/home.html",
                controller: function ($scope, $rootScope, $state, $location, $timeout, $window) {
                    $scope.first_university = {};
                }
            })
            .state("home.index", {
                url: "/home",
                templateUrl: "./views/sample_partials/index.html",
                controller: "IndexController"

            })
            .state("home.courses", {
                url: "/courses",
                templateUrl: "./views/sample_partials/courses/index.html",
                resolve: {
                    courses: function (appFactory) {
                        return appFactory.getAllCourses();
                    }
                },
                controller: function ($scope, courses, validatorFactory) {
                    $scope.courses = courses.courses;
                    console.log(courses);
                    $scope.courses_limit = 8;
                    $scope.courses_pagination = validatorFactory.paginate($scope.courses, 1, $scope.courses_limit);
                    let course = new Array();
                    $scope.search_course = '';
                    $scope.filterCourses = function (key) {
                        // filter by search function
                        course = new Array();
                        $scope.search_course = key;
                        for (var i = 0; i < $scope.courses.length; i++) {
                            let course_type = $scope.courses[i].name.toUpperCase();
                            let institute_type = $scope.courses[i].institution.name.toUpperCase();
                            if (course_type.search(key.toUpperCase()) > -1 || institute_type.search(key.toUpperCase()) > -1) {
                                course.push($scope.courses[i]);
                            }
                        }
                        //call the pagination function
                        $scope.courses_pagination = validatorFactory.paginate(course, 1, $scope.courses_limit);
                    };
                    $scope.CoursesPagerClicked = function (object) {
                        if (object.status === true) {
                            if ($scope.search_course.length > 0) {
                                $scope.courses_pagination = validatorFactory.paginate(course, object.page, $scope.courses_limit);
                            } else {
                                $scope.courses_pagination = validatorFactory
                                        .paginate($scope.courses, object.page, $scope.courses_limit);
                            }
                        }
                    };
                }
            })
            .state("home.institution", {
                url: "/institution",
                templateUrl: "./views/sample_partials/institution/institution-home.html",
                resolve: {
                    institution_data: function (appFactory) {
                        return appFactory.getAllInstitutions();
                    }
                },
                controller: function ($scope, $rootScope, validatorFactory, $state, $location, $timeout, $window, institution_data, utilityService, liteService, appFactory, sweetAlert) {
                    $scope.institution_data = institution_data;
                    $scope.new_institution = {name: ""};
                    $scope.submit = 'false';
                    $scope.feedback_institution = "";
                    $scope.institution_error = "";
                    let institution_page_limit = 8;
                    $scope.pagination_data = validatorFactory.paginate(institution_data.institutions, 1, institution_page_limit);
                    $scope.search_key = '';
                    let institution = new Array();
                    $scope.filterInstitution = function (key) {
                        // filter by search function
                        institution = new Array();
                        $scope.search_key = key;
                        for (var i = 0; i < $scope.institution_data.institutions.length; i++) {
                            let str = $scope.institution_data.institutions[i].name.toUpperCase();
                            if (str.search(key.toUpperCase()) > -1) {
                                institution.push($scope.institution_data.institutions[i]);
                            }
                        }
                        //call the pagination function
                        $scope.pagination_data = validatorFactory.paginate(institution, 1, institution_page_limit);
                    };
                    $scope.pagerClicked = function (object) {
                        if (object.status === true) {

                            if ($scope.search_key.length > 0) {
                                $scope.pagination_data = validatorFactory.paginate(institution, object.page, institution_page_limit);
                            } else {
                                $scope.pagination_data = validatorFactory.paginate($scope.institution_data.institutions, object.page, institution_page_limit);
                            }
                        }
                    };
                    $scope.addInstitutionToggle = false;
                    $scope.addInstitutionClick = function () {
                        $scope.addInstitutionToggle = !$scope.addInstitutionToggle;
                    };
                    $scope.addNewInstitution = function () {
                        //slight validation
                        var name_valid = validatorFactory.validateName($scope.new_institution.name, "institution");
                        if (!name_valid.status) {
                            $scope.feedback_institution = "has-error";
                            $scope.institution_error = name_valid.message;
                        } else {
                            $scope.feedback_institution = "";
                            $scope.institution_error = "";
                            $scope.submit = 'true';
                            liteService.set($scope.new_institution, "api/institution").then(function (resp) {
                                $scope.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    if (resp.data.status === "success") {
                                        utilityService.notifySuccess(resp.data.response.message);
                                        $scope.new_institution.name = "";
                                        updateInstitutionList();
                                        $scope.addInstitutionClick();
                                    }
                                    if (resp.data.status === "error") {
                                        $scope.feedback_institution = "has-error";
                                        $scope.institution_error = resp.data.response.message;
                                    }
                                }
                            }).catch(function (resp) {
                                var message = "An error occurred";
                                $scope.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    message = resp.data.response.message;
                                }
                                utilityService.notifyWarning(message);
                            });
                        }

                    };
                    function updateInstitutionList() {
                        appFactory.getAllInstitutions().then(function (resp) {
                            $scope.institution_data = resp;
                            $scope.pagination_data = validatorFactory.paginate(resp.institutions, 1, institution_page_limit);
                        });
                    }
                    ;
                    $scope.clearForm = function () {
                        $scope.feedback_institution = "";
                        $scope.institution_error = "";
                    };
                    $scope.deleteInstitution = function (i) {

                        var text = "Are you sure you want to permanently delete " + i.name + "?";
                        sweetAlert.swal({
                            title: "Are you sure!",
                            text: text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonText: "Yes",
                            cancelButtonText: "No",
                            closeOnConfirm: true,
                            closeOnCancel: true
                        }, function (isConfirm) {
                            if (isConfirm) {
                                var url = "api/institution/" + i.institutionid;
                                liteService.deleteObject(url).then(function (resp) {
                                    if (resp.data.status === "success") {
                                        utilityService.notifySuccess(resp.data.response.message);
                                        var scope_data = new Array();
                                        for (var x = 0; x < $scope.institution_data.institutions.length; x++) {
                                            if ($scope.institution_data.institutions[x].institutionid !== i.institutionid) {
                                                scope_data.push($scope.institution_data.institutions[x]);
                                            }
                                        }
                                        $scope.institution_data = {"institutions": scope_data};
                                        $scope.pagination_data = validatorFactory.paginate(scope_data, 1, institution_page_limit);
                                    }
                                    if (resp.data.status === "error") {
                                        utilityService.notifyWarning(resp.data.response.message);
                                    }

                                }).catch(function (resp) {
                                    utilityService.networkError();
                                });
                            }
                        });
                    };
                }
            })
            .state("home.institution.view", {
                url: "/:institutionid",
                templateUrl: "./views/sample_partials/institution/institution-view.html",
                resolve: {
                    institution: function (appFactory, $stateParams) {
                        return appFactory.getInstitutionById($stateParams.institutionid);
                    },
                    courses_data: function (appFactory, $stateParams) {
                        return appFactory.getInstitutionCourses($stateParams.institutionid);
                    },
                    students: function (appFactory, $stateParams) {
                        return appFactory.getInstitutionStudents($stateParams.institutionid);
                    }
                },
                controller: function ($scope, students, appFactory, institution, courses_data, utilityService, liteService, sweetAlert, validatorFactory) {

                    //------------------------------------
                    //      Declare Global Variables
                    //------------------------------------
                    $scope.institution = institution.institution;
                    $scope.courses_data = courses_data;
                    $scope.course_limit_length = 5;
                    $scope.course_pagination = validatorFactory.paginate($scope.courses_data.courses, 1, $scope.course_limit_length);
                    $scope.students = students.students;
                    $scope.students_limit_length = 5;
                    $scope.students_pagination = validatorFactory.paginate($scope.students, 1, $scope.students_limit_length);
                    //------------------------------------
                    //      Edit Instution
                    //------------------------------------
                    $scope.update_institution = {
                        "name": $scope.institution.name,
                        "institutionid": $scope.institution.institutionid
                    };
                    $scope.validate_institution = {
                        response_class: '',
                        response_message: '',
                        submit: 'false'
                    };
                    $scope.editInstitutionToggle = false;
                    $scope.editInstitutionClick = function () {
                        $scope.editInstitutionToggle = !$scope.editInstitutionToggle;
                    };
                    $scope.EditInstitution = function () {
                        //slight validation
                        var name_valid = validatorFactory.validateName($scope.update_institution.name, "institution");
                        if (!name_valid.status) {
                            $scope.validate_institution.response_class = "has-error";
                            $scope.validate_institution.response_message = name_valid.message;
                        } else {
                            $scope.validate_institution.response_class = "";
                            $scope.validate_institution.response_message = "";
                            $scope.validate_institution.submit = "true";
                            liteService.set($scope.update_institution, "api/institution/update").then(function (resp) {
                                $scope.update_institution.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    if (resp.data.status === "success") {
                                        utilityService.notifySuccess(resp.data.response.message);
                                        $scope.institution.name = $scope.update_institution.name;
                                        $scope.editInstitutionClick();
                                    }
                                    if (resp.data.status === "error") {
                                        $scope.validate_institution.response_class = "has-error";
                                        $scope.validate_institution.response_message = resp.data.response.message;
                                    }
                                }
                            }).catch(function (resp) {
                                var message = "An error occurred";
                                $scope.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    message = resp.data.response.message;
                                }
                                utilityService.notifyWarning(message);
                            });
                        }

                    };
                    $scope.clearEditForm = function () {
                        $scope.validate_institution.response_class = "";
                        $scope.validate_institution.response_message = "";
                        $scope.update_institution = {
                            "name": $scope.institution.name,
                            "institutionid": $scope.institution.institutionid
                        };
                    }
                    //--------------------------------
                    //      Course Section Start
                    //--------------------------------
                    let course = new Array();
                    $scope.search_institution_course = '';
                    $scope.courseFilter = function (key) {
                        // filter by search function
                        $scope.search_institution_course = key;
                        course = new Array();
                        for (var i = 0; i < $scope.courses_data.courses.length; i++) {
                            let str = $scope.courses_data.courses[i].name.toUpperCase();
                            if (str.search(key.toUpperCase()) > -1) {
                                course.push($scope.courses_data.courses[i]);
                            }
                        }
                        //call the pagination function
                        $scope.course_pagination = validatorFactory.paginate(course,
                                1, $scope.course_limit_length);
                    };
                    $scope.coursePagerClicked = function (object) {
                        if (object.status === true) {
                            if ($scope.search_institution_course.length > 0) {
                                $scope.course_pagination = validatorFactory.paginate(course,
                                        object.page, $scope.course_limit_length);
                            } else {
                                $scope.course_pagination = validatorFactory.paginate($scope.courses_data.courses,
                                        object.page, $scope.course_limit_length);
                            }
                        }
                    };

                    $scope.new_course = {
                        "institution": $scope.institution.institutionid,
                        "name": ""
                    };
                    $scope.course_validated = {
                        response_class: '',
                        response_message: '',
                        submit: 'false'
                    };
                    $scope.update_course_validated = {
                        response_class: '',
                        response_message: '',
                        submit: 'false'
                    };
                    $scope.update_course = {
                        "institution": $scope.institution.institutionid,
                        courseid: "",
                        "name": ""
                    };
                    $scope.addCourseToggle = false;
                    $scope.addCourseClick = function () {
                        $scope.addCourseToggle = !$scope.addCourseToggle;
                    };
                    $scope.addCourse = function () {
                        //simple validation
                        var course_valid = validatorFactory.validateName($scope.new_course.name, "Course");
                        if (!course_valid.status) {
                            $scope.course_validated.response_class = "has-error";
                            $scope.course_validated.response_message = course_valid.message;
                        } else {
                            //passed Validation
                            $scope.course_validated.response_class = "";
                            $scope.course_validated.response_message = course_valid.message;
                            $scope.course_validated.submit = 'true';
                            liteService.set($scope.new_course, "api/institution/" + $scope.institution.institutionid + "/courses").then(function (resp) {
                                $scope.course_validated.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    if (resp.data.status === "success") {
                                        $scope.addCourseClick();
                                        utilityService.notifySuccess(resp.data.response.message);
                                        $scope.new_course = {"institution": $scope.institution.institutionid, "name": ""};
                                        updateCourseList();
                                    }
                                    if (resp.data.status === "error") {
                                        $scope.course_validated.response_class = "has-error";
                                        $scope.course_validated.response_message = resp.data.response.message;
                                    }
                                }

                            }).catch(function (exception) {

                                var message = "An error occurred";
                                $scope.course_validated.submit = 'false';
                                if (exception !== null & exception.data !== null && exception.data.response !== null) {
                                    message = exception.data.response.message;
                                }
                                utilityService.notifyWarning(message);
                            });
                        }
                    };
                    $scope.deleteCourse = function (i) {
                        var text = "Are you sure you want to permanently delete " + i.name + "?";
                        sweetAlert.swal({
                            title: "Are you sure!",
                            text: text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonText: "Yes",
                            cancelButtonText: "No",
                            closeOnConfirm: true,
                            closeOnCancel: true
                        }, function (isConfirm) {
                            if (isConfirm) {

                                var url = "api/institution/" + $scope.institution.institutionid + "/courses/" + i.courseid;
                                liteService.deleteObject(url).then(function (resp) {
                                    if (resp !== null && resp.data.status !== null && resp.data.response !== null) {
                                        if (resp.data.status === "success") {
                                            utilityService.notifySuccess(resp.data.response.message);
//                                            updateCourseList();
                                            var scope_data = new Array();
                                            for (var x = 0; x < $scope.courses_data.courses.length; x++) {

                                                if ($scope.courses_data.courses[x].courseid !== i.courseid) {
                                                    scope_data.push($scope.courses_data.courses[x]);
                                                }
                                            }
                                            $scope.courses_data = {"courses": scope_data};
                                            $scope.course_pagination = validatorFactory.paginate(scope_data,
                                                    1, $scope.course_limit_length);
                                            $scope.institution.courses = $scope.courses_data.courses.length;
                                        }
                                        if (resp.data.status === "error") {
                                            utilityService.notifyWarning(resp.data.response.message);
                                        }
                                    } else {
                                        utilityService.notifyDanger(resp.data.response);
                                    }
                                }).catch(function (resp) {
                                    utilityService.networkError();
                                });
                            }
                        });
                    };
                    updateCourseList = function () {
                        appFactory.getInstitutionCourses($scope.institution.institutionid).then(function (resp) {
                            $scope.courses_data = resp;
                            $scope.institution.courses = $scope.courses_data.courses.length;
                            $scope.course_pagination = validatorFactory.paginate(resp.courses,
                                    1, $scope.course_limit_length);
                        });
                    };
                    $scope.clearAddCourseForm = function () {
                        $scope.course_validated.response_class = '';
                        $scope.course_validated.response_message = '';
                    };
//                    $scope.editCourse = function (c) {
//                        $('#modal-edit-course').modal('show');
//                        $scope.update_course = {
//                            institution: $scope.institution.institutionid,
//                            courseid: c.courseid,
//                            name: c.name
//                        };
//                    };
//                    $scope.updateCourse = function () {
//
//                        //simple validation
//                        var course_valid = validatorFactory.validateName($scope.update_course.name, "Course");
//                        if (!course_valid.status) {
//                            $scope.update_course_validated.response_class = "has-error";
//                            $scope.update_course_validated.response_message = course_valid.message;
//                        } else {
//                            //passed Validation
//                            $scope.update_course_validated.response_class = "";
//                            $scope.update_course_validated.response_message = course_valid.message;
//                            $scope.update_course_validated.submit = 'true';
//                            liteService.set($scope.update_course, "api/institution/" + $scope.institution.institutionid + "/courses/update").then(function (resp) {
//                                $scope.update_course_validated.submit = 'false';
//                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
//                                    if (resp.data.status === "success") {
//                                        utilityService.notifySuccess(resp.data.response.message);
////                                        $scope.new_course = {"institution": $scope.institution.institutionid, "name": ""};
//                                        updateCourseList();
//                                    }
//                                    if (resp.data.status === "error") {
//                                        $scope.update_course_validated.response_class = "has-error";
//                                        $scope.update_course_validated.response_message = resp.data.response.message;
//                                    }
//                                }
//
//                            }).catch(function (exception) {
//
////                                var message = "An error occurred";
////                                $scope.update_course_validated.submit = 'false';
////                                if (exception !== null & exception.data !== null && exception.data.response !== null) {
////                                    message = exception.data.response.message;
////                                }
//                                utilityService.networkError();//(message);
//                            });
//                        }
//                    };
                    //---------------------------------
                    //      Student Section Start
                    //---------------------------------
                    
                    let student = new Array();
                    $scope.search_institution_student = '';
                    $scope.studentsFilter = function (key) {
                        // filter by search function
                        student = new Array();
                        $scope.search_institution_student = key;
                        for (var i = 0; i < $scope.students.length; i++) {
                            let str = $scope.students[i].studentname.toUpperCase();
                            if (str.search(key.toUpperCase()) > -1) {
                                student.push($scope.students[i]);
                            }
                        }
                        //call the pagination function
                        $scope.students_pagination = validatorFactory.paginate(student,
                                1, $scope.students_limit_length);
                    };
                    $scope.studentPagerClicked = function (object) {
                        if (object.status === true) {
                            if ($scope.search_institution_student.length > 0) {
                                $scope.students_pagination = validatorFactory.paginate(student, object.page,
                                        $scope.students_limit_length);
                            } else {
                                $scope.students_pagination = validatorFactory.paginate($scope.students, object.page,
                                        $scope.students_limit_length);
                            }
                        }
                    };
                    $scope.new_student = {
                        "name": null,
                        "course": "null",
                        "date_of_birth": null
                    };
                    $scope.student_validated = {
                        name_response_class: '',
                        name_response_message: '',
                        course_response_class: '',
                        course_response_message: '',
                        dob_response_class: '',
                        dob_response_message: '',
                        submit: 'false'
                    };
                    $scope.addStudentToggle = false;
                    $scope.addStudentClick = function () {
                        $scope.addStudentToggle = !$scope.addStudentToggle;
                    };
                    $scope.addStudent = function () {
                        //validation
                        var name_valid = validatorFactory.validateName($scope.new_student.name, "student");
                        var date_valid = validatorFactory.validateDate($scope.new_student.date_of_birth, "Date of Birth");
                        var course_valid = validatorFactory.validateCourse($scope.new_student.course);
                        if (name_valid.status && date_valid.status && course_valid.status) {
                            //successful Validation
                            $scope.clearStudentErrors();
                            var d = Date.parse($scope.new_student.date_of_birth);
                            new_studnt = {
                                "name": $scope.new_student.name,
                                "course": parseInt($scope.new_student.course),
                                "dateOfBirth": d
                            };
                            $scope.student_validated.submit = 'false';
                            liteService.set(new_studnt, "api/institution/" + $scope.institution.institutionid + "/students").then(function (resp) {
                                $scope.student_validated.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    if (resp.data.status === "success") {
                                        utilityService.notifySuccess(resp.data.response.message);
                                        $scope.new_student = {
                                            "name": null,
                                            "course": "null",
                                            "date_of_birth": null
                                        };
                                        $scope.updateStudentList();
                                        updateCourseList();
                                        $scope.addStudentClick();
                                    }
                                    if (resp.data.status === "error") {
                                        $scope.course_validated.response_class = "has-error";
                                        $scope.course_validated.response_message = resp.data.response.message;
                                    }
                                }

                            }).catch(function (exception) {

                                var message = "An error occurred";
                                $scope.student_validated.submit = 'true';
                                if (exception !== null & exception.data !== null && exception.data.response !== null) {
                                    message = exception.data.response.message;
                                }
                                utilityService.notifyWarning(message);
                            });
                        } else {

                            //failed validation
                            //1.name
                            if (!name_valid.status) {
                                $scope.student_validated.name_response_class = "has-error";
                                $scope.student_validated.name_response_message = name_valid.message;
                            } else {
                                $scope.student_validated.name_response_class = "";
                                $scope.student_validated.name_response_message = name_valid.message;
                            }
                            //2.date
                            if (!date_valid.status) {
                                $scope.student_validated.dob_response_class = "has-error";
                                $scope.student_validated.dob_response_message = date_valid.message;
                            } else {
                                $scope.student_validated.dob_response_class = "";
                                $scope.student_validated.dob_response_message = date_valid.message;
                            }
                            //3.course
                            if (!course_valid.status) {
                                $scope.student_validated.course_response_class = "has-error";
                                $scope.student_validated.course_response_message = course_valid.message;
                            } else {
                                $scope.student_validated.course_response_class = "";
                                $scope.student_validated.course_response_message = course_valid.message;
                            }

                        }

                    };
                    $scope.updateStudentList = function () {
                        appFactory.getInstitutionStudents($scope.institution.institutionid).then(function (resp) {
                            $scope.students = resp.students;
                            $scope.institution.students = $scope.students.length;
                            $scope.students_pagination = validatorFactory.paginate($scope.students, 1, $scope.students_limit_length);
                        });
                    }
                    ;
                    $scope.clearStudentErrors = function () {
                        $scope.student_validated.name_response_class = "";
                        $scope.student_validated.name_response_message = "";
                        $scope.student_validated.dob_response_class = "";
                        $scope.student_validated.dob_response_message = "";
                        $scope.student_validated.course_response_class = "";
                        $scope.student_validated.course_response_message = "";
                    }
                    //ui-sref
                    //$state.go(sdfsf)
                }
            })
            .state("home.institution.view.course", {
                url: "/courses/:courseid",
                templateUrl: "./views/sample_partials/institution/course/view.html",
                resolve: {
                    course: function (appFactory, $stateParams) {
                        return appFactory.getCourseById($stateParams.institutionid, $stateParams.courseid);
                    }
                },
                controller: 'ViewInstitutionCourseController'
            })
            .state("home.institution.view.student", {
                url: "/students/:studentid",
                templateUrl: "./views/sample_partials/institution/student/view.html",
                resolve: {
                    student: function (appFactory, $stateParams) {
                        return appFactory.getStudentById($stateParams.institutionid, $stateParams.studentid);
                    }
                },
                controller: "StudentController"
            });
}
;
$(document).ready(function () {
    fixWrapperHeight(), setBodySmall();
});
$(window).bind("load", function () {
    $(".splash").css("display", "none")
});
$(window).bind("resize click", function () {
    setBodySmall(), setTimeout(function () {
        fixWrapperHeight()
    }, 300)
}), function () {
    angular.module("homer", ["ui.router", "ngSanitize", "ui.bootstrap", "angular-flot", "angles", "angular-peity", "cgNotify", "angles", "ngAnimate", "ui.map", "ui.calendar", "summernote", "ngGrid", "ui.tree", "bm.bsTour", "datatables", "xeditable", "ui.select", "ui.sortable", "ui.footable", "angular-chartist", "ui.codemirror"])
}();
angular.module("homer").config(configState);
angular.module("homer").run(function ($rootScope, $state, editableOptions) {
    $rootScope.$state = $state;
    editableOptions.theme = "bs3";
});
angular.module("homer").filter("propsFilter", propsFilter);
angular.module("homer").directive("pageTitle", pageTitle);
angular.module("homer").directive("sideNavigation", sideNavigation);
angular.module("homer").directive("minimalizaMenu", minimalizaMenu);
angular.module("homer").directive("sparkline", sparkline);
angular.module("homer").directive("icheck", icheck);
angular.module("homer").directive("panelTools", panelTools);
angular.module("homer").directive("panelToolsFullscreen", panelToolsFullscreen);
angular.module("homer").directive("smallHeader", smallHeader);
angular.module("homer").directive("animatePanel", animatePanel);
angular.module("homer").directive("landingScrollspy", landingScrollspy);
angular.module("homer").controller("appCtrl", appCtrl);
angular.module("homer").factory("sweetAlert", sweetAlert);
angular.module("homer").directive("touchSpin", touchSpin);
angular.module("homer").controller("IndexController", function () {
//    alert("welcome");
});


