package com.aptcoder.daily_expence_recoard

class DataItemClass {
    var productName:String=""
    var price:String=""
    constructor(){
        //for fire base
    }

    constructor(productName: String, price: String) {
        this.productName = productName
        this.price = price
    }
}