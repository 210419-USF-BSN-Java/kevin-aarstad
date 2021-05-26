let cookie = document.cookie;
console.log(cookie);
document.getElementById("pending").addEventListener("click", listPending);
document.getElementById("resolved").addEventListener("click", listResolved);
let url = 'http://localhost:8080/reimbursement/main/reimbursements';

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