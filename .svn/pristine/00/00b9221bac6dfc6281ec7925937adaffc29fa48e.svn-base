var Selection=(function () {
    var Selection = function (addSelectedFunc,removeSelectFunc, refreshFunc) {
        var selection =  {
            selectOptions: [],
            addSelectedFunc: addSelectedFunc,
            removeSelectFunc:removeSelectFunc,
            refreshFunc: refreshFunc,
            add:function (options) {
                if(this.addSelectedFunc){
                    var selectArray=this.addSelectedFunc();
                    for(var i=0;i<selectArray.length;i++){
                        if(this.indexOf(selectArray[i])==-1){
                            this.selectOptions.push(selectArray[i])
                        }
                    }

                }
                if(options){
                    for(var i=0;i<options.length;i++){
                        if(this.indexOf(options[i])==-1){
                            this.selectOptions.push(options[i])
                        }
                    }
                }
                this.refresh();
            },
            remove: function () {
                if (this.removeSelectFunc) {
                    var selectArray = this.removeSelectFunc();
                    const  arrLength=selectArray.length;
                    for (var i = 0; i < selectArray.length; i++) {
                        this.arrayRemove(selectArray[i]);
                    }
                    if(this.refreshFunc){
                        this.refreshFunc();
                    }
                }
            },
            refresh:function () {
                if(this.refreshFunc){
                    this.refreshFunc();
                }
            },
            indexOf: function (val) {
                for (var i = 0; i < this.selectOptions.length; i++) {
                    if (this.selectOptions[i].id == val.id) return i;
                }
                return -1;
            },
            arrayRemove: function (val) {
                var index = this.indexOf(val);
                if (index > -1) {
                    this.selectOptions.splice(index, 1);
                }
            },
            hasChoice:function () {
                return this.selectOptions.length>0;
            }

        };

        return selection;
    }

   return Selection;
}());