console.log("view-employees.js")
let url = 'http://localhost:8080/reimbursement/main/employees';
sendGetRequest(url);


function sendGetRequest(url){
    console.log("in view-employees sendGetRequest");
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let list = JSON.parse(this.responseText);
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
    }
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send();
}