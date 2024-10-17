/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */     /* global usersData, panelID */

var open = false;

    function openNav() {
            if (!open) {
                document.getElementById("mySidebar").style.width = "250px";
                document.getElementById("main").style.marginLeft = "250px";
                open = true;
            } else {
                closeNav();
            }
        }
    
function getCookie(cookieName) {
  const cookieString = document.cookie;
  const cookies = cookieString.split(';');

  for (const cookie of cookies) {
    const [key, value] = cookie.trim().split('=');
    if (key === cookieName) {
      return decodeURIComponent(value);
    }
  }

  return '';
}
    function signin()
    {
        let admin=getCookie("adminName");
        console.log(admin);
        if(admin){
           login();
        }
        else
        {
            showPanel('verification');
        }
    }

        function closeNav() {
            document.getElementById("mySidebar").style.width = "0";
            document.getElementById("main").style.marginLeft = "0";
            open = false;
        }

        function showPanel(panelId) {
            if(panelId==='verification')
            {
                document.getElementById('openBtn').disabled=true;
                document.getElementById('openBtn').hidden=true;
            }
            var panels = document.querySelectorAll('.panel');
            for (var i = 0; i < panels.length; i++) {
                panels[i].style.display = 'none';
            }
            
            document.getElementById(panelId).style.display = 'block';
        }
        
        function login(){
            let uid=document.getElementById('username');
            let pass=document.getAnimations('password');
            console.log(uid);
            console.log(pass);
            let verify=verification(uid,pass,"verify");
            if(verify)
            {   
            document.getElementById('openBtn').disabled=false;
            document.getElementById('openBtn').hidden=false;
            showPanel('home');
        }
        else
        {
            alert("Inccorect userid or password!!!");
            showPanel('verification');
        }
        }
      

        function userData(arg) {
        var xhr = new XMLHttpRequest();
  xhr.open("GET", `AdminDatabaseServlet?arg=${encodeURIComponent(arg)}`, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                var usersData = JSON.parse(xhr.responseText);
                populateUsersTable(usersData);
            }
        };
        xhr.send();
    }
    
    function populateUsersTable(usersData) {
        var tableBody = document.getElementById("usersTableBody");
    tableBody.innerHTML = "";
    for (var i = 0; i < usersData.length; i++) {
        var user = usersData[i];
        var row = tableBody.insertRow();
        row.insertCell(0).innerHTML = user.UserName;
        row.insertCell(1).innerHTML = user.Name;
        row.insertCell(2).innerHTML = user.MobileNumber;
        row.insertCell(3).innerHTML = user.Password;
    }
}
function clientsData(arg) {
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `AdminDatabaseServlet?arg=${encodeURIComponent(arg)}`, true);

  xhr.onreadystatechange = function() {
    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
      var usersData = JSON.parse(xhr.responseText);
      console.log(usersData);
      populateDeliveryPartnersTable(usersData);
    }
  };
  xhr.send();
}

function populateDeliveryPartnersTable(usersData) {
  const tableBody = document.getElementById('usersTableBody');
  tableBody.innerHTML = ''; // clear the table body

  usersData.forEach((user) => {
    const row = document.createElement('tr');
    const columnMapping = {
      name: 'name',
      MobileNumber: 'obile_number',
      Email: 'email',
      drivingLicence: 'driving_licence',
      goveId: 'gove_id',
      photo: 'photo',
      vehicleNumber: 'ehicle_number',
      vehicleRegistrationProof: 'ehicle_registration_proof',
      insurance: 'insurance',
    };

    Object.keys(columnMapping).forEach((key) => {
      const cell = document.createElement('td');
      cell.textContent = user[columnMapping[key]]? user[columnMapping[key]] : ''; // handle null or undefined
      row.appendChild(cell);
    });
    tableBody.appendChild(row);
  });
}