const myDiv = document.querySelector(".showResult");
const checkBtn = document.querySelector(".checkBtn");
const hiddenAnswear = document.querySelector("#correctAnswear");
const answearToCheck = document.querySelector(".answearToCheck");
const correctDiv = document.querySelector(".correctDiv");
const wrongDiv = document.querySelector(".wrongDiv");
const yourAnswear = document.querySelector(".yourAnswear");
const myBtnTest = document.querySelector(".myBtnTest");
const isTrue = document.querySelector(".isTrue");
myDiv.addEventListener("keypress",function (event){
    if (event.key === "Enter" && (myDiv.classList.contains("hideResult") === false)){
        event.preventDefault();
        myBtnTest.click();x
    }
})

checkBtn.addEventListener('click',function (){
    console.log(hiddenAnswear.value);
    console.log(answearToCheck.value);
    if (hiddenAnswear.value.toUpperCase() == answearToCheck.value.toUpperCase()){
        wrongDiv.classList.add("hideIt");
        isTrue.value = 1;
    }else {
        correctDiv.classList.add("hideIt");
        yourAnswear.innerHTML = "Your answear: " + answearToCheck.value;
    }

    myDiv.classList.remove("hideResult");
})


answearToCheck.addEventListener("keypress",function (event){
    if (event.key === "Enter"){
        checkBtn.click();
        event.stopPropagation();
    }
})

