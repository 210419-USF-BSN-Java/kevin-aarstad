let cookie = document.cookie;
document.getElementById("submit_reimb").addEventListener("click", addReimbursement);
let url = 'http://localhost:8080/reimbursement//main/emp-reimbursements';

function addReimbursement(){
    console.log("adding reimbursement")
    let amount = document.getElementById("amount").value;
    let type = document.getElementById("type").value;
    let description = document.getElementById("desc").value;
    let splitCookie = cookie.split(';');
    let userId = splitCookie[0].split('=')[1];

    let xhr = new XMLHttpRequest;
    xhr.open("POST", url);
    
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            document.getElementById("message").innerHTML = "Successfully submitted request";
        }
        else if (xhr.readyState == 4){
            document.getElementById("message").innerHTML = "Failed to submit request";

        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `amount=${amount}&type=${type}&description=${description}&userId=${userId}`;
    xhr.send(requestBody);
}