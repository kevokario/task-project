angular.module("homer").factory("validatorFactory", function () {
    var factory = {};
    //name validate
    factory.validateName = function (name, type) {
        var response = {
            status: false,
            message: 'this field is required'
        };
        if (name === null || name.length === 0) {
            response.message = type + " name is required!";
        } else if (name.length > 0 && name.length < 3) {
            response.message = type + " name should be atleast 3 characters long!";
        } else {
            response.message = "";
            response.status = true;
        }
        return response;
    };
    //validate course
    factory.validateCourse = function (course) {
        var response = {
            status: false,
            message: 'this field is required'
        };
        if (course === null || course === "" || course === "null" || course ==="--select a course --") {
            response.message = "Please select a course!";
        } else {
            response.message = "";
            response.status = true;
        }
        return response;
    };
    //validate institution
    factory.validateInstitution = function (institution) {
        var response = {
            status: false,
            message: 'this field is required'
        };
        if (institution === null || institution ==="" || institution === "null") {
            response.message = "Please select an institution!";
        } else {
            response.message = "";
            response.status = true;
        }
        return response;
    };
    
    factory.validateDate = function (date, type) {
        var response = {
            status: false,
            message: 'this field is required'
        };
        if (date === null || date.length === 0) {
            response.message = "Please provide "+type;
        } else {
            response.message = "";
            response.status = true;
        }
        return response;
    };
    
    //paginator
    factory.paginate = function (list, pageNumber,limit) {
        var response = {
            next: {status: false, page: 0},
            prev: {status: false, page: 0},
            data: new Array(),
            page_number: 1,
            pages: 1,
            result:0
        };
        let length = limit;
        let data_list = new Array();
        //return boolean next, boolean prev,data to be served

        //calculate number of pages
        let pages = Math.floor(list.length / length); 
        if( (list.length % length) > 0){
            pages = pages+1;
        }
        let beginIndex = length * (pageNumber - 1);
        let limitIndex = beginIndex + length;

        for (let a = beginIndex; a < limitIndex; a++) {
            if (list[a] === undefined) {
                break;
            } else {
                data_list.push(list[a]);
            }
        }
        //set total number of pages
        response.pages = pages;
        //set current page number
        response.page_number = pageNumber;
        //set current data table
        response.data = data_list;
        response.result = list.length;

        let prev_page = pageNumber - 1;
        let next_page = pageNumber + 1;

        //set next page info
        if ( next_page <= pages && next_page > 0 ) {
            response.next.status = true;
            response.next.page = next_page;
        } else {
            response.next.status = false;
            response.next.page = 0;
        }
       //set prev page info
         if( prev_page <= pages && prev_page > 0 ){
            response.prev.status=true;
            response.prev.page = prev_page;
        } else {
            response.prev.status = false;
            response.prev.page = 0;
        }

        return response;
    };

    return factory;
});