function validateForm() {
    var errorCounter = 0;
    errorCounter += validateEmail();
    errorCounter += validateRepeatPass();

    if (errorCounter > 0) {
        return false;
    }
    return true;
}

function validateEmail() {
    var errorCounter = 0;
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var emailFld = document.getElementById("email");
    var emailErr = document.getElementById("emailError");
    var validationErr = document.getElementById("validationErr");

    if (emailFld.value <= 0) {
        validationErr.style.display = "none";
        emailFld.style.background = '#FF0000';
        emailErr.style.display = "block";
        emailErr.style.color = "#FF0000";
        errorCounter++;
    } else {
        if (!re.test(String(emailFld.value).toLowerCase())) {
            emailErr.style.display = "none";
            emailFld.style.background = '#FF0000';
            validationErr.style.display = "block";
            validationErr.style.color = "#FF0000";
            errorCounter++;
        } else {
            emailFld.style.background = '#90EE90';
            emailErr.style.display = "none";
            validationErr.style.display = "none";
        }
    }

    return errorCounter;
}

function validateRepeatPass() {
    var errorCounter = 0;
    var passFld = document.getElementById("password");
    var repeatPassFld = document.getElementById("repeatPassword");
    var repeatPassErr = document.getElementById("repeatPassError");
    var matchingErr = document.getElementById("matchingError");

    if (repeatPassFld.value.length <= 0) {
        matchingErr.style.display = "none";
        repeatPassFld.style.background = '#FF0000';
        repeatPassErr.style.display = "block";
        repeatPassErr.style.color = "#FF0000";
        errorCounter++;
    } else {
        if (passFld.value.localeCompare(repeatPassFld.value) !== 0) {
            repeatPassErr.style.display = "none";
            repeatPassFld.style.background = '#FF0000';
            matchingErr.style.display = "block";
            matchingErr.style.color = "#FF0000";
            errorCounter++;
        } else {
            repeatPassFld.style.background = '#90EE90';
            matchingErr.style.display = "none";
        }
    }

    return errorCounter;
}







