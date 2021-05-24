let token = sessionStorage.getItem("token");

if (!token) {
	window.location.href  = "http://localhost:8080/reimbursement/static/login.html";
} 
else {
	let tokenArr = token.split(":");
	if (tokenArr.length == 2) {
		let baseUrl = "http://localhost:8080/reimbursement/api/users/";
		sendGet(baseUrl + tokenArr[0], displayName);
	} else {
		window.location.href  = "http://localhost:8080/reimbursement/static/login.html";
	}
}

function sendGet(url, callback) {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function(){
		if(this.readyState===4 && this.status===200){
			callback(this);
		} else if (this.readyState===4){
			window.location.href="http://localhost:8080/reimbursement/static/login.html";
		}
	}
	xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

function displayName(xhr) {
	let user = JSON.parse(xhr.response);
	document.getElementById("user").innerHTML = `Welcome Manager: ${user.username}`;
}