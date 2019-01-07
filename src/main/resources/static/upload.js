function initFileInput(ctrlName, uploadUrl, service) {
    var control = $('#' + ctrlName);
    control.fileinput({
        theme: 'explorer',
        language: 'zh',
        uploadUrl: uploadUrl,
        allowedFileExtensions: ['jpg', 'png'],
        enctype: 'multipart/form-data',
        dropZoneEnabled: true,
        showUpload: false,
        browseClass: "btn btn-primary",
        previewFileIcon: '<i class="fa fa-file"></i>',
        msgFilesTooMany: 1,
        maxFileCount: 1,
        uploadLabel: "上传",
        uploadExtraData: {
            service: service
        }
    }).on("fileuploaded", function (event, data) {
    }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
        alert('文件上传失败！' + msg);
    });
}

initFileInput('content-image', 'upload', 'content');
initFileInput('style-image', 'upload', 'style');

