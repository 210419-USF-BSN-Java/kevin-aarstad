document.getElementById("login_btn").addEventListener("click", loginUser);

function loginUser(){
    console.log("attempting login");
	let user = document.getElementById("username").value;
	let pass = document.getElementById("password").value;
    let url = 'http://localhost:8080/reimbursement/static/login.html';

    let xhr = new XMLHttpRequest();
	xhr.open("POST", url);

	xhr.onreadystatechange = function(){
        console.log("readyState= "+ xhr.readyState +" status= "+ xhr.status);
		if(xhr.readyState == 4 && xhr.status == 200){
			let auth = xhr.getResponseHeader("Authorization");
            sessionStorage.setItem("token", auth);
            let userToken = auth.split(":");
            let role = Number(userToken[1]);
            console.log("role= " + userToken[1]);
            if (role == 1){
                window.location = 'http://localhost:8080/reimbursement/static/employee.html';
            }
            else if (role == 2){
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