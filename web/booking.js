/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

var distanse=document.getElementById('distanse');
var d = null;
var p = null;
var valuees=0;
var pickup=document.getElementById('pickup');
    var delivery=document.getElementById('delivery');
    const dateInput = document.getElementById('date');
    const today = new Date();
    dateInput.min = today.toISOString().split('T')[0];
    function showPanel(panelId)
    {
        let login=getCookie("username");
        console.log(login);
        if(login)
        {
            
                var panels = document.querySelectorAll('.panel');
                for (var i = 0; i < panels.length; i++) {
                    panels[i].style.display = 'none';
                }   
                var panel = document.getElementById(panelId);
                if(panel)
                    {         
                document.getElementById(panelId).style.display = 'block';
            }
            
        }
        else{
            window.location.href="login.html";
        }
            }
function check(event)
{
    event.preventDefault();
    if(pickup.value===delivery.value)
{
    alert("Pickup nd Delivery address can not be same");
    document.getElementById('pickup').focus();
}
else if(pickup.value==='other')
    {
        alert("Select a pickup address!!!");
        document.getElementById('pickup').focus();
    }
    else if(delivery.value==='other')
        {
            alert("Select a delsivery address!!!");
            document.getElementById('delivery').focus();
        }
    else
    {
        calcDistance();
    }
}
function calcDistance()
{ 
    valuees=parseInt(pickup.value)-parseInt(delivery.value);
    for(i=1;i<=4;i++)
    {
        if(i===Math.abs(valuees))
        {
            d=i*20;
            var dis=document.getElementById('distanse');
            dis.textContent = d;
            console.log(d);
            break;
        }
    }    
    price();
}
function price()
{
    var veh=document.getElementById('Svehicle');
    var vehicle=document.getElementById('vehicle').value;
    if(vehicle==='null')
        {
            alert('Please select a vehicle');
            vehicle.focus();
        }
    else if(vehicle==='smallTruck')
    {
        p=d*25;
        veh.value="Small Truck";
    }
    else if(vehicle==='mediumTruck')
    {
        p=d*40;
        veh.value="Medium Truck";
    }
    else if(vehicle==='largeTruck')
    {
        p=d*50;
        veh.value="Large Truck";
    }
    else
    {
        p=d*20;
        veh.value="Bike";
    }
    console.log(p);
    var pr=document.getElementById('fair');
    pr.value=p;
    showPanel('pricing');
}
let orderIdCounter = 0;
const orderIds = [];

function generateOrderId() {
  let orderId;
  do {
    orderId = `ORD${Math.floor(Math.random() * 1000000)}`;
  } while (orderIds.includes(orderId));
  orderIds.push(orderId);
  return orderId;
}
    // Function to get a cookie by name
   
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

const username = getCookie('username');
console.log(username); // Output: JohnDoe
    // Get the 'username' cookie
function showOrderConfirmation(event) {
    event.preventDefault();
    let userId= getCookie("username");
  const orderId = generateOrderId();
  const customerName = document.getElementById('name').value;
  const customerAddress = document.getElementById('address').value;
  const riderName = 'John Smith'; // hardcoded for demo purposes
  const vehicleType = document.getElementById('Svehicle').value;
  const riderNumber = '555-123-4567'; // hardcoded for demo purposes
  const paymentMethod = document.getElementById('payMode').value;
  const paymentStatus = 'Paid';
  const totalCost = document.getElementById('fair').value;
  const paymentDate = new Date().toLocaleString();
  const items = document.getElementById('goods').value; // hardcoded for demo purposes
  const subtotal = p; // hardcoded for demo purposes
  const gst=12;
  const tax = gst*p/100; // hardcoded for demo purposes
  const total = p+tax; // hardcoded for demo purposes
  insertion=orderSave(orderId,customerName,customerAddress,riderName,vehicleType,items,paymentMethod,paymentStatus,total,userId);
    document.getElementById('orderId').textContent = orderId;
  document.getElementById('customerName').textContent = customerName;
  document.getElementById('customerAddress').textContent = customerAddress;
  document.getElementById('riderName').textContent = riderName;
  document.getElementById('vehicleType').textContent = vehicleType;
  document.getElementById('riderNumber').textContent = riderNumber;
  document.getElementById('paymentMethod').textContent = paymentMethod;
  document.getElementById('paymentStatus').textContent = paymentStatus;
  document.getElementById('totalCost').textContent = totalCost;
  document.getElementById('paymentDate').textContent = paymentDate;
  document.getElementById('items').textContent = items;
  document.getElementById('subtotal').textContent = subtotal;
  document.getElementById('tax').textContent = tax;
  document.getElementById('total').textContent = total;
  showPanel('orderConfirmation');
  
}
function orderSave(...args) {
  const xhr = new XMLHttpRequest();
  const queryString = args.map((arg, index) => `arg${index + 1}=${encodeURIComponent(arg)}`).join('&');
  xhr.open("GET", `BookingServlet?${queryString}`, true);
  let response;
  xhr.onreadystatechange = function() {
    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
      response = xhr.responseText.trim();
    }
  };
  xhr.send();
  // Note: You can't return the response directly, as it's asynchronous
  // Instead, consider using a callback or promise
  return new Promise(resolve => {
    xhr.onreadystatechange = function() {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        resolve(xhr.responseText.trim());
      }
    };
  });
}
function disable(ide)
            {
              document.getElementById(ide).disabled = true;
            }
function reload()
{
    window.location.reload();
}