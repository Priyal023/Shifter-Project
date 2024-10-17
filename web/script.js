document.getElementById("login_button").addEventListener("click", function() {
  window.open("login.html", "_blank");
});
click=1;
function cookie(){
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

    // Get the 'username' cookie
    let username = getCookie("username");
    console.log("Username Cookie: ", username);
    function loginCheck()
    {
        if(username)
        {
            return 0;
        }
        else
        {
            window.location.href="login.html";
        }
    }

    if (username) 
    {
        document.getElementById('login_button').disabled=true;
        document.getElementById('login_button').hidden=true;
        const newItem=document.createElement("label");
        const loginBtn=document.getElementById('login_button');
        newItem.id="newLabel";
        newItem.textContent="Hello MR.  "+username;
        loginBtn.parentNode.replaceChild(newItem,loginBtn);
        document.getElementById('btnlogout').disabled=false;
        document.getElementById('btnlogout').hidden=false;
    } 
    else 
    {
        document.getElementById('login_button').hidden=false;
        document.getElementById('login_button').disabled=false;
    }
    
};
const form = document.querySelector('#locationSubmit');

form.addEventListener('click', (event) => {
  // Prevent the default form submission behavior
  event.preventDefault();

  // Get the selected city and area values from the form
  const city = document.querySelector('#city').value;
  const area = document.querySelector('#area').value;

  // Display the selected city and area values on the page
  if(city==='other'||area==='other'){
    alert("Please select both City and Area");
  } 
  else{
    servletCalling(city, area);
  }
});

document.getElementById("twoWheeler").addEventListener('click', function() {
  window.open("booking.html", "_blank");
});

document.getElementById("delivery_partners").addEventListener("click", function() {
  window.open("deliverypartner.html", "_blank");
});
document.getElementById("personal").addEventListener("click", function() {
  window.open("adminModule.html", "_blank");
});

function servletCalling(city,area) {
  function makeRequest(city, area) {
    var xhr = new XMLHttpRequest();

    // Configure XMLHttpRequest
    xhr.open('GET', 'HelloServlet', true); // Assuming servlet mapping is HelloServlet

    // Set up a function to handle the response
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Request was successful, handle the response
                document.getElementById('output').innerText = xhr.responseText;
            } else {
                // There was an error with the request
                console.error('Request failed with status:', xhr.status);
            }
        }
    };

    // Send the request
    xhr.send();
    return 0;
  }

  makeRequest(city, area);
}
/*
function isNumberKey(evt) {
  var charCode = (evt.which) ? evt.which : event.keyCode;

  if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    return false;
  }
  return true;
}

const cars = document.querySelectorAll('.car');

cars.forEach((car, index) => {
  car.style.animation = `zigzag ${Math.random() * 5 + 2}s infinite`;
  car.style.animationDelay = `${index * 0.5}s`;
});

function zigzag() {
  let angle = 0;
  let y = 0;

  return function() {
    angle += 10;
    y = Math.sin(angle) * 20;

    this.style.transform = `translateY(${y}px) translateX(${this.offsetLeft}px)`;
  }
}
*/
cars.forEach(car => {
  car.addEventListener('animationiteration', zigzag);
});

// Add event listener to hide login button after successful login
const loginForm = document.querySelector('#loginForm');

function logout()
{
    document.cookie = 'username=; max-age=0';
    location.reload();
}