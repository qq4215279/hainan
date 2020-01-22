$(function () {
    $(".clip-group").each(function () {
        var saveInputId = $(this).find("input.saveInput").attr("id");

        var img = $(this).find("img");
        var imgId = saveInputId + "ImgId";
        img.attr("id", imgId);
        var width = img.data("width");
        var height = img.data("height");

        var chooseBtnId = saveInputId + "ChooseBtn";
        $(this).find("input.choose-btn").attr("id", chooseBtnId);
        $(this).find("label.choose-label").attr("for", chooseBtnId);

        giveClip(width*2, height*2, imgId,  chooseBtnId, saveInputId);
    });


    function giveClip(width, height, imageId, chooseBtnId, saveInput) {
        var cancelId = chooseBtnId + "CancelBtn";
        var clipBtnId = chooseBtnId + "ClipBtn";
        var clipAreaId = chooseBtnId + "ClipArea";
        var clipImageLayerId = chooseBtnId + "ImageLayer";

        $(".container").append($('<div class="modal fade" id="' + clipImageLayerId + '" style="height: 100%;"><div id="' + clipAreaId + '" style="height: 100%;"></div><div style="position: absolute;top:10px;left: 10px;"><button class="btn btn-primary" id="' + clipBtnId + '" type="button"><i class="fa fa-check"></i>截取</button><button class="btn btn-error" id="' + cancelId + '" type="button"><i class="fa fa-reply"></i>取消</button></div></div>'));
        $("#" + cancelId).click(function () {
            $("#" + clipImageLayerId).modal("hide");
            $("#"+chooseBtnId).val("");
        });

        new PhotoClip("#" + clipAreaId, {
            size: [width, height],
            file: "#"+chooseBtnId,
            ok: "#" + clipBtnId,
            loadStart: function () {
                $("#" + clipImageLayerId).modal("show")
            },
            loadComplete: function () {

            },
            done: function (dataURL) {
                $("#" + imageId).attr("src", dataURL);
                $("#" + imageId).show();
                $("#"+saveInput).val(dataURL);
                $("#"+chooseBtnId).val("");
                $("#" + clipImageLayerId).modal("hide")
            },
            fail: function (msg) {
            }
        });
    }
});