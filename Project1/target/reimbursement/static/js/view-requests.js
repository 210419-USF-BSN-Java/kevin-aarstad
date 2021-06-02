let cookie = document.cookie;
console.log(cookie);
document.getElementById("pending").addEventListener("click", listPending);
document.getElementById("resolved").addEventListener("click", listResolved);
document.getElementById("allbyId").addEventListener("click", listAllById);
document.getElementById("resolve_reimb").addEventListener("click", resolveRequest);
let url = 'http://localhost:8080/reimbursement/main/reimbursements';


function listAllById(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url)
    let authorId = document.getElementById("emp_id").value;
    console.log("author id= " + authorId);
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let reimbursements = JSON.parse(this.responseText);
            for(let reimb of reimbursements){

            if (reimb.author == authorId){
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
                $('#list_empReimb').append(info);
            }
            }
        }
    }
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send();
}

function listPending(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url)
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let reimbursements = JSON.parse(this.responseText);
            for(let reimb of reimbursements){
            if(reimb.status == 1){
                let status = "";
                let type = "";
                
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
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send();
}

function listResolved(){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url)
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let reimbursements = JSON.parse(this.responseText);
            for(let reimb of reimbursements){
            if(reimb.status == 2 || reimb.status == 3){
                let status = "";
                let type = "";
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
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send();
}


function resolveRequest(){
    console.log("In resolveRequest");
    let status = document.getElementById('approve_deny').value;
    let reimbId = document.getElementById('reimbId').value;
    let resolverId = 0;
    console.log(cookie);
    let splitCookie = cookie.split(';');
    console.log(splitCookie);
    for(let i=0; i<splitCookie.length; i++){
        crumb = splitCookie[i].split('=');
        if(crumb[0] == 'userId' || crumb[0] == ' userId'){           
            resolverId = crumb[1];
        }
    }
    console.log("resolver id=" + resolverId);
    console.log("status=" + status);
    console.log("reimbId=" + reimbId);
    let rUrl = 0;
    if(status == 2){
        console.log("if status == 2");
        rUrl = 'http://localhost:8080/reimbursement/main/approve-reimbursement';
    }
    else if(status == 3){
        console.log("if status == 3")
        rUrl = 'http://localhost:8080/reimbursement/main/deny-reimbursement';
    }

    console.log("rUrl=" + rUrl)
    let xhr = new XMLHttpRequest;
    xhr.open("POST", rUrl, true);
    console.log("xhr.open()")
    xhr.onreadystatechange = function(){
        console.log("readyState= "+ xhr.readyState +" status= "+ xhr.status);
        if(xhr.readyState == 4 && xhr.status == 200){
            // let splitCookie = cookie.split(';');
            // let resolverId = splitCookie[0].split('=')[1];

            //let cookie = document.cookie;

            document.getElementById("message").innerHTML="Reimbursment resolved!";
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let requestBody = `id=${reimbId}&resolverId=${resolverId}`;
    xhr.send(requestBody);

}