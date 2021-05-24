let token = sessionStorage.getItem("token");
let content = document.getElementById("reimbursements");
document.getElementById("pending").addEventListener("click", listPending);
document.getElementById("resolved").addEventListener("click", listResolved);
document.getElementById("all").addEventListener("click", listAll);

if (!token){
    window.location.href = "http://localhost:8080/reimbursements/static/login.html";
}
else {
    let tokenArray = token.split(":");
    if(tokenArray.length == 2){
        let base = "http://localhost:8080/reimbursement/api/reimbursements/all";
    }
    else{
        window.location.href = "http://localhost:8080/reimbursements/static/login.html";
    }
}

function listAll(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", base)
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let list = xhr.getResponseHeader("allReimb");
            let reimbursements = JSON.parse(list);
            for(let reimb of reimbursements){
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
    xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

function listPending(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", base)
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let list = xhr.getResponseHeader("allReimb");
            let reimbursements = JSON.parse(list);
            for(let reimb of reimbursements){
                let status = "";
                let type = "";
            if(reimb.status == 1){
                
                status = "Pending";

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
                let info = $(`
                    <tr>
                        <td>${reimb.id}</td>
                        <td>${reimb.amount}</td>
                        <td>${reimb.description}</td>
                        <td>${submitTime}</td>
                        <td>${reimb.author}</td>
                        <td>${type}</td>
                        <td>${status}</td>
                    </tr>
                `)
                $('#list_pending').append(info);
            }    
            }
        }
    }
    xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

function listResolved(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", base)
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let list = xhr.getResponseHeader("allReimb");
            let reimbursements = JSON.parse(list);
            for(let reimb of reimbursements){
                let status = "";
                let type = "";
            if(reimb.status == 2 || reimb.status == 3){
                switch (reimb.status){
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
                $('#list_resolved').append(info);
            }
            }
        }
    }
    xhr.setRequestHeader("Authorization", token);
	xhr.send();
}