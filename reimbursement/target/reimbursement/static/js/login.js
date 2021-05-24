document.getElementById("login_btn").addEventListener("click", loginUser);

function loginUser(){
    console.log("attempting login");
    let user = document.getElementById("username").value;
    console.log(user);
    let pass = document.getElementById("password").value;
    console.log(pass);
    let xhr = new XMLHttpRequest();
    let url = 'http://localhost:8080/reimbursement/static/login.html';

    xhr.open("POST", url);

    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let auth = xhttp.getResponseHeader("Authorization");
            sessionStorage.setItem("token", auth);
            let userToken = auth.split(":");
            let role = Number(userToken[1]);
            if (role == 1){
                window.location = 'http://localhost:8080/reimbursement/static/employee.html';
            }
            else {
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