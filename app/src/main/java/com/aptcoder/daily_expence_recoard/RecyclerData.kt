package com.aptcoder.daily_expence_recoard

class RecyclerData {
    var oneDayData:ArrayList<DataItemClass>?=null

    constructor(){
        //for fire base
    }

    constructor(oneDayData: ArrayList<DataItemClass>?) {
        this.oneDayData = oneDayData
    }
}