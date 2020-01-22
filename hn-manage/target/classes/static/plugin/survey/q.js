function addItem(obj) {
    let num = $(obj).prev().find(".option-item-key").text();
    num = getBigLetterIndex(num);
    let n = 0;
    if(num && num !== ''){
        n = parseInt(num);
    }

    $(obj).before(optionHtml);
    $(obj).prev().find(".option-item-key").text(getBigLetter(n+1));
    let optionNum = $(obj).parent().find(".option-item").length-1;
    $(obj).prev().css("top", (optionNum*15)+"%");
    cssControl();
}

function addQuestion(obj) {
    $(obj).parents(".question-all").after(questionHtml);
    cssControl();
    questionNumControl();
    num2Letter();
}

function delItem(obj) {
    $(obj).parents(".option-item").remove();
}

function delQuestion(obj) {
    let questions = $(".question-all");
    if(questions.length <= 1){
        App.error("唯一的题目不能删除");
        return;
    }
    $(obj).parents(".question-all").remove();
    questionNumControl();
}

function questionNumControl() {
    let all = $(".question-all");
    all.each(function (i) {
        all.eq(i).attr("alt",i+1)
        all.eq(i).find(".question .position").text(i+1);
    });
}

function cssControl(){
    $("input[type=text]").addClass("border-hide");
    $(".question-all .option .option-item .option-item-text").css("min-width","200px");
}

function save(data){
    let url = App.ctxPath + "survey/doSave";
    $.ajax({
        async: false,
        url:url,
        data:JSON.stringify(data),
        type:"post",
        dataType:"json",
        contentType:"application/json",
        success:function (res) {
            App.success("提交成功!");
            // SurveyEdit.close();
            location.href = "/hn-admin/survey";
            Survey.table.refresh();
        },
        error:function(err){
            App.error("提交失败!" + err.responseJSON.message + "!");
        }
    });
}

function num2Letter(){
    $("span.letter").each(function (i) {
        let n = parseInt($(this).text());
        let letter = getBigLetter(n);
        $(this).text(letter);
    })

    $("input.letter").each(function (i) {
        let n = parseInt($(this).val());
        let letter = getBigLetter(n);
        $(this).attr("value",letter);
    })
}

function getBigLetterIndex(o) {
    return big_letter.findIndex(item=> item===o);
}

function getSmallLetterIndex(o) {
    return small_letter.findIndex(item=> item===o);
}

function getBigLetter(n){
    return big_letter[n];
}

function getSmallLetter(n){
    return small_letter[n];
}