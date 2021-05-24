let token = sessionStorage.getItem("token");

if (!token){
    window.location.href = "http://localhost:8080/reimbursements/static/login.html";
}
else {
    let tokenArray = token.split(":");
    if(tokenArray.length == 2){
        let base = "http://localhost:8080/reimbursement/api/employees/all";
        sendGetRequest(base);
    }
    else{
        window.location.href = "http://localhost:8080/reimbursements/static/login.html";
    }
}

function sendGetRequest(url){
    console.log("in view-employees sendGetRequest");
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function(){

        if(xhr.readyState == 4 && xhr.status == 200){
            let employeeList = xhr.getResponseHeader("allEmp");
            let list = JSON.parse(employeeList);
            console.log(list);
            for(let emp of list){
                let info = $(`
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.username}</td>
                        <td>${emp.firstName}</td>
                        <td>${emp.lastName}</td>
                        <td>${emp.email}</td>
                    </tr>
                `)
                $('#emp_list').append(info);
            }
        }
        else if (xhr.readyState == 4){
            window.location.href = "http://localhost:8080/reimbursements/static/login.html";
        }
    }
    xhr.setRequestHeader("Authorization", token);
    xhr.send();
}