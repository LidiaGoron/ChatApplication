package com.example.login

//Aceasta clasa este utilizata pentru a "abstractiza" un user
// Data class este utilizata doar pentru a retine valori, insa pentru ca folosim FireBase va trebui sa avem un constructor gol, de aceea
//nu folosim Data class ci o clasa normala
 class User {
    var name:String?=null // toate sunt initializate la null
    var email:String?=null
    var uid:String?=null

    constructor(){} // Constructor gol necesar cand folosim FireBase


        constructor(name:String?,email:String?,uid:String?){ //constructor pt initializare
            this.name=name
            this.email=email
            this.uid=uid
        }

}