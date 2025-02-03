const errors = {};
const regexPseudo = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
const regexPassword = /^(?=.*[A-Z])(?=.*\d)(?=.*['";:,.\/?\\\-])[A-Za-z\d'";:,.\/?\\\-]{8,16}$/;

const checkPseudo = (input) => {    
    if (!regexPseudo.test(input.value)) {
        getTableRowError(0).innerText = "error pseudo"
    } else {
         getTableRowError(0).innerText = ""
    }
    
}

const checkPassword = (input) => {
    if (!regexPassword.test(input.value)) {
        getTableRowError(1).innerText = "error password"
    } else {
         getTableRowError(1).innerText = ""
    }
}

const checkConfirmPassword = (input) => {
    const password = document.getElementById("password").value;
    if(input.value  != password || !regexPassword.test(input.value)) {
        getTableRowError(2).innerText = "password don't match"
    } else {
        getTableRowError(2).innerText = ""
    }

}


const getTableRowError = (n) => {
    return document.querySelector("table").rows[n].getElementsByClassName("error")[0];
}















/*

            */