document.getElementById("login_btn").addEventListener("click", loginUser);

function loginUser(){
    console.log("attempting login");
	let user = document.getElementById("username").value;
	let pass = document.getElementById("password").value;
    let url = 'http://localhost:8080/reimbursement/login';

    let xhr = new XMLHttpRequest();
	xhr.open("POST", url);

	xhr.onreadystatechange = function(){
        console.log("readyState= "+ xhr.readyState +" status= "+ xhr.status);
		if(xhr.readyState == 4 && xhr.status == 200){
            let cookie = document.cookie;
            console.log(cookie);
            let splitCookie = cookie.split(';');
            console.log(splitCookie);
            let role = 0;
            for(let i=0; i<splitCookie.length; i++){//let crumb of splitCookie
                crumb = splitCookie[i].split('=');
                console.log("crumb=" + crumb);
                console.log("crumb[0]=" + crumb[0]);
                if(crumb[0] == 'userRole' || crumb[0] == ' userRole'){
                    console.log("inside crumb[0] == userRole");
                    role = crumb[1];
                }
            }
            console.log("role=" + role);
            if (role == '1'){
                console.log("sending to employee page");
                window.location = 'http://localhost:8080/reimbursement/static/employee.html';
            }
            else if (role == '2'){
                console.log("sending to manager page");
                window.location = 'http://localhost:8080/reimbursement/static/manager.html';
            }
        }
        else {
           document.getElementById("message").innerHTML="Invalid Login";
        }
    }
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `username=${user}&password=${pass}`;
	xhr.send(requestBody);
}