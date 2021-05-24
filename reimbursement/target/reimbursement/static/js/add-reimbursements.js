document.getElementById('submit').addEventListener("click", addReimbursement);

function addReimbursement(){
    let amount = document.getElementById("amount");
    let type = document.getElementById("type");
    let description = document.getElementById("desc");

    let token = sessionStorage.getItem("token");
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/reimbursement/submit";
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
	xhr.setRequestHeader("Authorization",token);
	let requestBody = `amount=${amount}&type=${type}&description=${description}`;
	xhr.send(requestBody);
}