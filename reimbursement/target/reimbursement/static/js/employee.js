let token = sessionStorage.getItem("token");
document.getElementById("empPending").addEventListener("click", listPending);
document.getElementById("empResolved").addEventListener("click", listResolved);

if (!token) {
	window.location.href  = "http://localhost:8080/reimbursement/static/login.html";
} else {
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
	document.getElementById("user").innerHTML = `Welcome Employee: ${user.username}!`;
}

function listPending(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/reimbursements/api/reimbs/author/");
    xhr.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            let list = xhr.getResponseHeader("allPendingReimb");
            let reimbursements = JSON.parse(list);
            console.log(reimbursements);
            for(let reimb of reimbursements){
            if(reimb.status == 1){    
                let status = "";
                let type = "";
            
                switch (reimb.status){
                    case 1:
                        status = "Pending";
                        break;
                    case 2:
                        status = "Approved";
                        break;
                    case 3:
                        status = "Denied";
                        break;
                }
                switch(reimb.type){
                    case 1:
                        type = "Meal";
                        break;
                    case 2:
                        type = "Equipment";
                        break;
                    case 3:
                        type = "Travel";
                        break;
                    case 4:
                        type = "Other";
                        break;
                }
                let submitTime = new Date(reimb.submitted);
                let resolveTime = new Date(reimb.resolved);
                let info = $(`
                    <tr>
                        <td>${reimb.id}</td>
                        <td>${reimb.amount}</td>
                        <td>${reimb.description}</td>
                        <td>${submitTime}</td>
                        <td>${reimb.resolver}</td>
                        <td>${type}</td>
                        <td>${status}</td>
                    </tr>
                `)
                $('#list_all').append(info);
            }
            }
        }
    }
    xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

function listResolved(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/reimbursements/api/reimbs/author/");
    xhr.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            let list = xhr.getResponseHeader("allPendingReimb");
            let reimbursements = JSON.parse(list);
            console.log(reimbursements);
            for(let reimb of reimbursements){
            if(reimb.status == 2 || reimb.status == 3){    
                let status = "";
                let type = "";
            
                switch (reimb.status){
                    case 1:
                        status = "Pending";
                        break;
                    case 2:
                        status = "Approved";
                        break;
                    case 3:
                        status = "Denied";
                        break;
                }
                switch(reimb.type){
                    case 1:
                        type = "Meal";
                        break;
                    case 2:
                        type = "Equipment";
                        break;
                    case 3:
                        type = "Travel";
                        break;
                    case 4:
                        type = "Other";
                        break;
                }
                let submitTime = new Date(reimb.submitted);
                let resolveTime = new Date(reimb.resolved);
                let info = $(`
                    <tr>
                        <td>${reimb.id}</td>
                        <td>${reimb.amount}</td>
                        <td>${reimb.description}</td>
                        <td>${submitTime}</td>
                        <td>${reimb.author}</td>
                        <td>${resolveTime}</td>
                        <td>${reimb.resolver}</td>
                        <td>${type}</td>
                        <td>${status}</td>
                    </tr>
                `)
                $('#list_all').append(info);
            }
            }
        }
    }
    xhr.setRequestHeader("Authorization", token);
	xhr.send();
}