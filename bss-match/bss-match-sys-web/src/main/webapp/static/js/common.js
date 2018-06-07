function DateFormatter(value){
	if(null==value || value=='') return ' -- ';
	if(!(value instanceof Number)){
		value = parseInt(value);
	}
	var unixTimestamp = new Date(value);
	return nowTime(unixTimestamp,24);
}
function nowTime(ev,type){
	/*
	 * ev:显示时间的元素
	 * type:时间显示模式.若传入12则为12小时制,不传入则为24小时制
	 */
	//年月日时分秒
	var Y,M,D,W,H,I,S;

		var d=ev;
		var Week=['星期天','星期一','星期二','星期三','星期四','星期五','星期六']
		Y=d.getFullYear();
		M=fillZero(d.getMonth()+1);
		D=fillZero(d.getDate());
		W=Week[d.getDay()];
		H=fillZero(d.getHours());
		I=fillZero(d.getMinutes());
		S=fillZero(d.getSeconds());
		//12小时制显示模式
		if(type && type==12){
			//若要显示更多时间类型诸如中午凌晨可在下面添加判断
			if(H<=12){
				H='上午 '+H;
			}else if(H>12 && H<24){
				H-=12;
				H='下午 '+fillZero(H);
			}else if(H==24){
				H='下午 00';
			}
		}
		return Y+'-'+M+'-'+D+' '+H+':'+I+':'+S;

}

//月日时分秒为单位时前面补零
function fillZero(v){
	if(v<10){v='0'+v;}
	return v;
}
function DateFormatterNoTime(value){
	if(null==value || value=='') return ' -- ';
	if(!(value instanceof Number)){
		value = parseInt(value);
	}
	var unixTimestamp = new Date(value);
	return unixTimestamp.toLocaleDateString();
}

var dictJsonStore = {};
function initDictJsonStore(ctx,path){
	if(dictJsonStore[path]){
		return ;
	}
	$.ajax({
	   type: "POST",
	   url: ctx+"/sys/dict/findSubsByPath",
	   data: "path="+path,
	   async : false,
	   success: function(data){
		 var temp = {};
	     for(var i=0; i<data.length; i++){
	    	 temp[data[i].code]=data[i].name;
	     }
	     dictJsonStore[path]=temp;
	   }
	});
}

function dictFormatter(ctx,path,value){
	if(dictJsonStore[path]){
		return dictJsonStore[path][value];
	}else{
		initDictJsonStore(ctx,path);
		return dictJsonStore[path][value];
	}
}

jQuery.prototype.serializeObject=function(){  
    var a,o,h,i,e;  
    a=this.serializeArray();  
    o={};  
    h=o.hasOwnProperty;  
    for(i=0;i<a.length;i++){  
        e=a[i];  
        if(!h.call(o,e.name)){  
            o[e.name]=e.value;  
        }  
    }  
    return o;  
};

/**  
 * 将数值四舍五入(保留2位小数)后格式化成金额形式  
 *  
 * @param num 数值(Number或者String)  
 * @return 金额格式的字符串,如'1,234,567.45'  
 * @type String  
 */    
function formatCurrency(num) {
	if(num!=0 && (!num)){
		return " -- ";
	}
    num = num.toString().replace(/\$|\,/g,'');    
    if(isNaN(num))    
    num = "0";
    sign = (num == (num = Math.abs(num)));    
    num = Math.floor(num*100+0.50000000001);    
    cents = num%100;    
    num = Math.floor(num/100).toString();    
    if(cents<10)    
    cents = "0" + cents;    
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)    
    num = num.substring(0,num.length-(4*i+3))+','+    
    num.substring(num.length-(4*i+3));    
    return (((sign)?'':'-') + num + '.' + cents);    
}
