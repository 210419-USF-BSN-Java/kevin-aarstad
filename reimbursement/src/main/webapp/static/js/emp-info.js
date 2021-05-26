let url = 'http://localhost:8080/reimbursement/main/employee-info';
sendGetRequest(url);
document.getElementById("fNameUpdate").addEventListener("click", updateFirstName);
document.getElementById("lNameUpdate").addEventListener("click", updateLastName);
document.getElementById("passwordUpdate").addEventListener("click", updatePassword);
document.getElementById("emailUpdate").addEventListener("click", updateEmail);

let cookie = document.cookie;
console.log(cookie);
let splitCookie = cookie.split(';');
console.log(splitCookie);
let userId = 0;
for(let i=0; i<splitCookie.length; i++){
    crumb = splitCookie[i].split('=');
    if(crumb[0] == 'userId' || crumb[0] == ' userId'){           
        userId = crumb[1];
    }
}

let emp = null;
let firstName = null;
let lastName = null;
let password = null;
let email = null;

function sendGetRequest(url){
    console.log("in view-employees sendGetRequest");
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            emp = JSON.parse(this.responseText);
            firstName = emp.firstName;
            lastName = emp.lastName;
            password = emp.password;
            email = emp.password;
            console.log(emp);
                let info = $(`
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.username}</td>
                        <td>${emp.firstName}</td>
                        <td>${emp.lastName}</td>
                        <td>${emp.email}</td>
                    </tr>
                `)
            $('#emp_info').append(info);
        }
    }
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send();
}

function updateFirstName(){
    let xhr = new XMLHttpRequest;
    xhr.open("POST", url);
    firstName = document.getElementById("firstName").value;
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            document.getElementById("fNameMessage").innerHTML="successfully changed First Name!";
        }
        else if (xhr.readyState == 4){
            document.getElementById("fNameMessage").innerHTML="Failed to change First Name!";
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `firstName=${firstName}&lastName=${lastName}&password=${password}&email=${email}&userId=${userId}`;
    xhr.send(requestBody);
}

function updateLastName(){
    let xhr = new XMLHttpRequest;
    xhr.open("POST", url);
    lastName = document.getElementById("lastName").value;
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            document.getElementById("lNameMessage").innerHTML="successfully changed Last Name!";
        }
        else if (xhr.readyState == 4){
            document.getElementById("lNameMessage").innerHTML="Failed to change Last Name!";
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `firstName=${firstName}&lastName=${lastName}&password=${password}&email=${email}&userId=${userId}`;
    xhr.send(requestBody);
}

function updatePassword(){
    let xhr = new XMLHttpRequest;
    xhr.open("POST", url);
    password = document.getElementById("password").value;
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            document.getElementById("passwordMessage").innerHTML="successfully changed Password!";
        }
        else if (xhr.readyState == 4){
            document.getElementById("passwordMessage").innerHTML="Failed to change Password!";
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `firstName=${firstName}&lastName=${lastName}&password=${password}&email=${email}&userId=${userId}`;
    xhr.send(requestBody);
}

function updateEmail(){
    let xhr = new XMLHttpRequest;
    xhr.open("POST", url);
    email = document.getElementById("email").value;
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            document.getElementById("emailMessage").innerHTML="successfully changed Email!";
        }
        else if (xhr.readyState == 4){
            document.getElementById("emailMessage").innerHTML="Failed to change Email!";
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `firstName=${firstName}&lastName=${lastName}&password=${password}&email=${email}&userId=${userId}`;
    xhr.send(requestBody);
}