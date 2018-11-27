export const postService = {
  submitPost,
  submitPostCredential
};

function submitPost(multimedia) {
  const userToken = getCookie("token");
  console.log(multimedia);
  const promiseArray = multimedia.map(
    el =>
      new Promise((resolve, reject) => {
        const data = new FormData();
        data.append("media", el, el.name);

        const xhttpreq = new XMLHttpRequest();

        xhttpreq.onreadystatechange = () => {
          if (xhttpreq.readyState === 4) {
            if (xhttpreq.status === 200 || xhttpreq.status === 201) resolve(JSON.parse(xhttpreq.response));
            else reject("Error for this object => ", el.name);
          }
        };

        let fileType = 1;
        switch (el.type.split("/")[0]) {
          case "video":
            fileType = 2;
            break;
          case "audio":
            fileType = 3;
            break;
          default:
            break;
        }

        console.log("​submitPost -> fileType", fileType);

        xhttpreq.open(
          "POST",
          "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/media/" + fileType + "/",
          true
        );
        xhttpreq.timeout = 50000;
        xhttpreq.setRequestHeader("Authorization", "JWT " + userToken);

        xhttpreq.send(data);
      })
  );

  return Promise.all(promiseArray);
}

function submitPostCredential(mediaIDS, story, format, title, tags) {
  const userToken = getCookie("token");

  return new Promise((resolve, reject) => {
    const data = {
      media: mediaIDS,
      story,
      format,
      title,
      tags
    };

    const xhttpreq = new XMLHttpRequest();

    xhttpreq.onreadystatechange = () => {
      if (xhttpreq.readyState === 4) {
        if (xhttpreq.status === 200 || xhttpreq.status === 201) resolve(JSON.parse(xhttpreq.response));
        else reject(JSON.parse(xhttpreq.response));
      }
    };

    xhttpreq.open("POST", "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/create1/", true);
    xhttpreq.setRequestHeader("Content-Type", "application/json");
    xhttpreq.setRequestHeader("Authorization", "JWT " + userToken);

    xhttpreq.send(JSON.stringify(data));
  });
}

function getCookie(key) {
    var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return keyValue ? keyValue[2] : null;
}
