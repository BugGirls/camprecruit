/*
 * 6 到 18 位，以字母开头，字母数字下划线组成的字符串
 */
function isRegisterUserName(str)
{
var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){5,17}$/;
if (!patrn.test(str)) {
	return false;
}
return true;
}

/*
 * 6 到 18 位，字母数字下划线组成的字符串
 */
function isRegisterUserNameNew(str)
{
	var patrn=/^([a-zA-Z0-9]|[._]){6,18}$/;
	if (!patrn.test(str)) {
		return false;
	}
	return true;
}


function ispwd(str){
	var pattern=/^[A-Za-z0-9]{6,18}$/;
	if (!pattern.test(str)) return false;
	return true;
}

function isChinese(obj)
{   
  var str = obj.replace(/(^\s*)|(\s*$)/g,'');
  if (!(/^[\u4E00-\uFA29]*$/.test(str)    
          && (!/^[\uE7C7-\uE7F3]*$/.test(str))))    
  {    
      return false;    
  }    
  return true;    
}    
   
   
function isEmail(str)    
{    
    if(/^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]+$/.test(str))    
    {    
        return true;
    }    
    return false;    
}    
   
function isImg(str)    
{    
    var objReg = new RegExp("[.]+(jpg|jpeg|swf|gif)$", "gi");    
    if(objReg.test(str))    
    {    
        return true;    
    }    
    return false;    
}   

function isInteger(str)    
{    
    if(/^-?\d+$/.test(str))    
    {    
        return true;    
    }    
    return false;    
}    
   
   
function isFloat(str)    
{    
    if(/^(-?\d+)(\.\d+)?$/.test(str))
    {    
        return true;    
    }    
    return false;    
}    
   
   
function isPost(str)    
{    
    if(/^\d{1,6}$/.test(str))    
    {    
        return true;    
    }    
    return false;    
 
}    

function isMobile(str)    
{    
    if(/^(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$/.test(str))    
      {    
          return true;    
      }    
    return false;    
}    
   
   
function isPhone(str)    
{    
    if(/^(0[1-9]\d{1,2}-)\d{7,8}(-\d{1,8})?/.test(str))    
    {    
        return true;    
    }    
    return false;    
} 

function isQQ(str){    
    if(/^\d{4,12}$/.test(str))    
    {    
        return true;    
    }    
    return false;    
}    
   
function isDate(str)    
{    
    var reg = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;    
    if(reg.test(str))    
    {    
        return true;    
    }    
    return false;      
}  

/*
 * 身份证号
 */
function isIdCardNo(idNumber)    
{    
    var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);    
    var varArray = new Array();    
    var lngProduct = 0;    
    var intCheckDigit;    
  
   
    if ((idNumber.length != 15) && (idNumber.length != 18))    
    {    
        return false;    
    }      
    for(i=0;i<idNumber.length;i++)    
    {    
        varArray[i] = idNumber.charAt(i);    
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17))    
        {    
            return false;    
        }    
        else if (i < 17)    
        {    
            varArray[i] = varArray[i]*factorArr[i];    
        }    
    }    
    if (idNumber.length == 18)    
    {    
        var date8 = idNumber.substring(6,14);    
        if (checkDate(date8) == false)    
        {    
            return false;    
        }          
        for(i=0;i<17;i++)    
        {    
            lngProduct = lngProduct + varArray[i];    
        }          
        intCheckDigit = 12 - lngProduct % 11;    
        switch (intCheckDigit)    
        {    
            case 10:    
                intCheckDigit = 'X';    
                break;    
            case 11:    
                intCheckDigit = 0;    
                break;    
            case 12:    
                intCheckDigit = 1;    
                break;    
        }          
        if (varArray[17].toUpperCase() != intCheckDigit)    
        {    
            return false;    
        }    
    }    
    else   
    {          
        var date6 = idNumber.substring(6,12);    
        if (checkDate(date6) == false)    
        {    
            return false;    
        }    
    }    
    return true;    
}    
/*
 * 字符串的endWith方法。
 */
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str){
		return true;
	}  
	else
	  return false;

	}

String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(0,str.length)==str){
		return true;
	}  
	else
	  return false;
	}

/*
 *校验特殊字符。  （个人可以添加！）
 */
function CheckStr(str){
    var SpecialCharacters = "@/'\"#$%&^*";
    var i =0;
    for ( i = 0; i < SpecialCharacters.length - 1; i++)
    {
        if (str.IndexOf(SpecialCharacters.charIndex(i)) != -1)
        {
            return true;
        }
    }
    return false;
}


