angular.module("homer").controller("ViewInstitutionCourseController",
        function (course, $scope, utilityService, validatorFactory, liteService) {
            $scope.course = course.courses[0];
            $scope.limit_length = 5;
            
            $scope.update_course_validated = {
                response_class: '',
                response_message: '',
                submit: 'false'
            };
            $scope.updateCourse = {
                "institution": $scope.course.institution.institutionid,
                courseid: $scope.course.courseid,
                "name": $scope.course.name
            };
            $scope.editCourseToggle = false;
            $scope.editCourseClick = function () {
                $scope.editCourseToggle = !$scope.editCourseToggle;
            };
            $scope.updateCourseData = function () {

                //simple validation
                var course_valid = validatorFactory.validateName($scope.updateCourse.name, "Course");
                if (!course_valid.status) {
                    $scope.update_course_validated.response_class = "has-error";
                    $scope.update_course_validated.response_message = course_valid.message;
                } else {
                    //passed Validation
                    $scope.update_course_validated.response_class = "";
                    $scope.update_course_validated.response_message = course_valid.message;
                    $scope.update_course_validated.submit = 'true';
                    liteService.set($scope.updateCourse, "api/institution/" + $scope.institution.institutionid + "/courses/update").then(function (resp) {
                        $scope.update_course_validated.submit = 'false';
                        if (resp !== null & resp.data !== null && resp.data.response !== null) {
                            if (resp.data.status === "success") {
                                utilityService.notifySuccess(resp.data.response.message);
//                                        $scope.new_course = {"institution": $scope.institution.institutionid, "name": ""};
                                updateCourseList();
                                $scope.course.name = $scope.updateCourse.name;
                                $scope.editCourseClick();
                            }
                            if (resp.data.status === "error") {
                                $scope.update_course_validated.response_class = "has-error";
                                $scope.update_course_validated.response_message = resp.data.response.message;
                            }
                        }

                    }).catch(function (exception) {

                        var message = "An error occurred";
                        $scope.update_course_validated.submit = 'false';
                        if (exception !== null & exception.data !== null && exception.data.response !== null) {
                            message = exception.data.response.message;
                        }
                        utilityService.notifyWarning(message);
                    });
                }
            };

           
            $scope.academic_level = [
                {id: 1, name: "Certificate Level"},
                {id: 2, name: "Diploma Level"},
                {id: 3, name: "Bachelor's Level"},
                {id: 4, name: "Masters Level"},
                {id: 5, name: "PHD Level"},
            ];

            $scope.paginate_academic_level = validatorFactory.paginate($scope.academic_level, 1, $scope.limit_length);
            $scope.AcademicLevelPagerClicked = function (object) {
                if (object.status === true) {
                    $scope.paginate_academic_level = validatorFactory.paginate($scope.academic_level, object.page, $scope.limit_length);
                }
            };

            $scope.academic_years = [
                {id: 1, a_level: $scope.academic_level[0].name, a_year: "Year 1"},
                {id: 2, a_level: $scope.academic_level[0].name, a_year: "Year 2"},
                {id: 3, a_level: $scope.academic_level[1].name, a_year: "Year 1"},
                {id: 4, a_level: $scope.academic_level[1].name, a_year: "Year 2"},
                {id: 5, a_level: $scope.academic_level[2].name, a_year: "Year 1"},
                {id: 6, a_level: $scope.academic_level[2].name, a_year: "Year 2"},
            ];
            $scope.paginate_academic_year = validatorFactory.paginate($scope.academic_years, 1, $scope.limit_length);
            $scope.AcademicYearPagerClicked = function (object) {
                if (object.status === true) {
                    $scope.paginate_academic_year = validatorFactory.paginate($scope.academic_years, object.page, $scope.limit_length);
                }
            };

            $scope.units = [
                {
                    id: 1,
                    code: 'CSC111',
                    name: "Introduction to Computing",
                    points: 3,
                    a_level: $scope.academic_level[0].name,
                    a_year: $scope.academic_years[0].a_year,
                    semester: 'Semester 1'
                },
                {
                    id: 2,
                    code: 'CSC112',
                    name: "Introduction to Programming",
                    points: 3,
                    a_level: $scope.academic_level[0].name,
                    a_year: $scope.academic_years[0].a_year,
                    semester: 'Semester 1'
                },
                {
                    id: 3,
                    code: 'MAT113',
                    name: "Discrete Mathematics",
                    points: 3,
                    a_level: $scope.academic_level[0].name,
                    a_year: $scope.academic_years[0].a_year,
                    semester: 'Semester 1'
                },
                {
                    id: 4,
                    code: 'COS101',
                    name: "Communication Skills",
                    points: 3,
                    a_level: $scope.academic_level[0].name,
                    a_year: $scope.academic_years[0].a_year,
                    semester: 'Semester 1'
                }
            ];
            $scope.paginate_units = validatorFactory.paginate($scope.units, 1, $scope.limit_length);
            $scope.UnitPagerClicked = function (object) {
                if (object.status === true) {
                    $scope.paginate_units = validatorFactory.paginate($scope.units, object.page, $scope.limit_length);
                }
            };
            console.log($scope.course);
            $scope.update_course = {
                "institution": $scope.institution.institutionid,
                courseid: "",
                "name": ""
            };
        }
);

angular.module("homer").controller("StudentController", function ($scope, $state, student, appFactory, students, validatorFactory, liteService, utilityService, sweetAlert) {
    //------------------------
    //    Global Variables
    //------------------------
    $scope.content = student;
    $scope.students = students;
    $scope.response = $scope.content.response;
    if ($scope.response === "present") {
        $scope.student = $scope.content.student;
        $scope.student_institution = {"institutionid": student.institution.id, "name": student.institution.name};
        $scope.institution_courses = {"courses": []};
        var date = new Date($scope.student.student_dob);
        //-----------------------------
        //    Personal Info Section
        //-----------------------------
        $scope.personal_info = {
            course: $scope.student.course.courseid,
            name: $scope.student.studentname,
            dateOfBirth: date
        };
        $scope.personal_valid = {
            name_response_class: '',
            name_response_message: '',
            date_response_class: '',
            date_response_message: '',
            submit: 'false'
        };
        $scope.personalInfoToggle = false;
        $scope.togglePersonalInfo = function () {
            $scope.personalInfoToggle = !$scope.personalInfoToggle;
        };
        $scope.submitPersonalInfo = function () {
            //validate
            var name_valid = validatorFactory.validateName($scope.personal_info.name, "student");
            var date_valid = validatorFactory.validateDate($scope.personal_info.dateOfBirth, "Date of Birth");
            if (name_valid.status && date_valid.status) {
                //successful validation
                var d = Date.parse($scope.personal_info.dateOfBirth);
                var post_data = {
                    course: {courseid: $scope.student.course.courseid},
                    name: $scope.personal_info.name,
                    studentid: $scope.student.studentid,
                    dateOfBirth: d
                };
                $scope.personal_valid.submit = 'true';
                liteService.set(post_data, "api/institution/" + $scope.institution.institutionid + "/students/update").then(function (resp) {
                    $scope.personal_valid.submit = 'false';
                    if (resp !== null & resp.data !== null && resp.data.response !== null) {
                        if (resp.data.status === "success") {
                            utilityService.notifySuccess(resp.data.response.message);
                            $scope.togglePersonalInfo();
                            var date = new Date(d);
                            $scope.personal_info = {
                                course: $scope.student.course.courseid,
                                name: post_data.name,
                                dateOfBirth: date
                            };
                            $scope.student.studentname = post_data.name;
                            $scope.student.student_dob = d;
                        }
                        if (resp.data.status === "error") {
                            $scope.personal_valid.response_class = "has-error";
                            $scope.personal_valid.response_message = resp.data.response.message;
                        }
                    }
                }).catch(function (exception) {
                    var message = "An error occurred";
                    $scope.personal_valid.submit = 'false';
                    if (exception !== null & exception.data !== null && exception.data.response !== null) {
                        message = exception.data.response.message;
                    }
                    utilityService.notifyWarning(message);
                });
            } else {
                //failed validation
                utilityService.notifyWarning("Validation Failed");
            }
        };
        $scope.clearPersonalInfoErrors = function () {
            $scope.personal_valid.name_response_class = "";
            $scope.personal_valid.name_response_message = "";
            $scope.personal_valid.date_response_class = "";
            $scope.personal_valid.date_response_class = "";
        };
        //-----------------------------
        //    Academic Info Section
        //-----------------------------
        $scope.academic_info = {
            institution: {institutionid: "", name: ""},
            course: {
                "courseid": '',
                "name": ""
            }
        };
        $scope.academic_valid = {
            course_response_class: '',
            course_response_message: '',
            campus_response_class: '',
            campus_response_message: '',
            submit: 'false'
        };
        $scope.academicInfoToggle = false;
        $scope.toggleAcademicInfo = function () {
            $scope.academicInfoToggle = !$scope.academicInfoToggle;
        };
        $scope.getCourses = function (id) {
            appFactory.getInstitutionCourses(JSON.parse($scope.academic_info.institution).institutionid).then(function (resp) {
                $scope.institution_courses = resp;
            }).catch(function (resp) {

                utilityService.networkError();
            });
        };
        $scope.UpdateAcademicInfo = function () {
            //validate 

            var campus_valid = validatorFactory.validateInstitution($scope.academic_info.institution.institutionid);
            var course_valid = validatorFactory.validateCourse($scope.academic_info.course.name);
            if (course_valid.status && campus_valid.status) {
                $scope.clearAcademicErrors();
                //successful validation
                var d = Date.parse($scope.personal_info.dateOfBirth);
                var course_json = JSON.parse($scope.academic_info.course);
                var post_data = {
                    course: {courseid: course_json.courseid, name: course_json.name},
                    name: $scope.student.studentname,
                    studentid: $scope.student.studentid,
                    dateOfBirth: d
                };
                var current_university = $scope.student_institution;
                var new_university = JSON.parse($scope.academic_info.institution);
                var current_course = $scope.student.course.name;
                var new_course = {courseid: JSON.parse($scope.academic_info.course).courseid, name: JSON.parse($scope.academic_info.course).name};
                //if universities are different?
                var message = "";
                var refresh = "unset";
                if (current_university.institutionid !== new_university.institutionid) {
                    message = "You are about to transfer this student to institution : " + new_university.name
                            + " and Asign course : " + new_course.name + " Proceed with Action?";
                    refresh = "new_institution";
                } else if (current_university.institutionid === new_university.institutionid && current_course !== new_course.name) {
                    message = "You are about to transfer this student from course :" + current_course + " to course :" + new_course.name + " Proceed with action?";
                    refresh = "new_course";
                } else {
                    message = "You made no new changes. Nothing will be saved!";
                }

                if (refresh === "unset") {
                    utilityService.notifyWarning(message);
                } else {
                    sweetAlert.swal({
                        title: "Are you sure!",
                        text: message,
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonText: "Yes",
                        cancelButtonText: "No",
                        closeOnConfirm: true,
                        closeOnCancel: true
                    }, function (isConfirm) {
                        if (isConfirm) {
                            //make apost request
                            var url = "";
                            if (refresh === "new_institution") {
                                url = "api/institution/" + $scope.institution.institutionid + "/students/update-institution";
                            }
                            if (refresh === "new_course") {
                                url = "api/institution/" + $scope.institution.institutionid + "/students/update";
                            }
                            liteService.set(post_data, url).then(function (resp) {
                                $scope.personal_valid.submit = 'false';
                                if (resp !== null & resp.data !== null && resp.data.response !== null) {
                                    if (resp.data.status === "success") {
                                        $scope.toggleAcademicInfo();
                                        utilityService.notifySuccess(resp.data.response.message);
                                        if (refresh === "new_course") {
                                            var json_decode = JSON.parse($scope.academic_info.course);
                                            $scope.student.course.name = json_decode.name;
                                            $scope.student.course.courseid = json_decode.courseid;
                                        }
                                        if (refresh === "new_institution") {
                                            $scope.content = {"student": [], "response": "absent"};
                                        }

                                    }
                                    if (resp.data.status === "error") {
                                        utilityService.notifyWarning(resp.data.response.message);
                                    }
                                }
                            }).catch(function (exception) {
                                var message = "An error occurred";
                                $scope.personal_valid.submit = 'false';
                                if (exception !== null & exception.data !== null && exception.data.response !== null) {
                                    message = exception.data.response.message;
                                }
                                utilityService.notifyWarning(message);
                            });
                        }
                    });
                }

                //if university is same but different course


                //if nothing has changed



            } else {
                //failed Validation
                if (course_valid.status === false) {
                    $scope.academic_valid.course_response_class = "has-error";
                    $scope.academic_valid.course_response_message = course_valid.message;
                } else {
                    $scope.academic_valid.course_response_class = "";
                    $scope.academic_valid.course_response_message = "";
                }
                //campus validation
                if (!campus_valid.status) {
                    $scope.academic_valid.campus_response_class = "has-error";
                    $scope.academic_valid.campus_response_message = campus_valid.message;
                } else {
                    $scope.academic_valid.course_response_class = "";
                    $scope.academic_valid.course_response_message = "";
                }

            }

        };
        $scope.clearAcademicErrors = function () {
            $scope.academic_valid.course_response_class = "";
            $scope.academic_valid.course_response_message = "";
            $scope.academic_valid.campus_response_class = "";
            $scope.academic_valid.campus_response_message = "";
        }

        $scope.deleteStudent = function () {
            sweetAlert.swal({
                title: "Are you sure!",
                text: "You are about to delete this student, Proceed with action?",
                type: "warning",
                showCancelButton: true,
                confirmButtonText: "Yes",
                cancelButtonText: "No",
                closeOnConfirm: true,
                closeOnCancel: true
            }, function (isConfirm) {

                if (isConfirm) {

                    var url = "api/institution/" + student.institution.id + "/students/" + student.student.studentid;

                    liteService.deleteObject(url).then(function (resp) {
                        if (resp.data.status === "success") {
                            utilityService.notifySuccess(resp.data.response.message);
                            $scope.updateStudentList();
                            $scope.content = {"student": [], "response": "absent"};
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
    ;
    $scope.updateStudentList = function () {
        appFactory.getInstitutionStudents($scope.institution.institutionid).then(function (resp) {
            $scope.students = resp.students;
            $scope.institution.students = $scope.students.length;
        });
    };
});



