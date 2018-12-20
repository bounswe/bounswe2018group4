import { postConstants } from "../_constants";
import { postService } from "../_services";
import { alertActions } from "./";

export const postActions = {
  submitPost
};

function submitPost(multimedia, story, format, title, tags) {
  return dispatch => {
    dispatch(request());

    postService
      .submitPost(multimedia)
      .then(values => {
        dispatch(success(values));
        const ids = values.map(el => el.id);
        dispatch(submitPostCredentials(ids, story, format, title, tags));
      })
      .catch(res => {
        dispatch(failure(res.err));
        dispatch(alertActions.error("Check request"));
      });
  };

  function request() {
    return { type: postConstants.SUBMIT_POST_REQUEST };
  }
  function success(values) {
    return { type: postConstants.SUBMIT_POST_SUCCESS, payload: values };
  }
  function failure(err) {
    return { type: postConstants.SUBMIT_POST_FAILURE, err };
  }
}

function submitPostCredentials(ids, story, format, title, tags) {
  return dispatch => {
    dispatch(request());

    postService
      .submitPostCredential(ids, story, format, title, tags)
      .then(res => {
        dispatch(success(res));
        dispatch(alertActions.success("Posting is done"));
      })
      .catch(res => {
        dispatch(failure(res.err));
        dispatch(alertActions.error("Check request"));
      });
  };

  function request() {
    return { type: postConstants.SUBMIT_POST_CREDENTIALS_REQUEST };
  }
  function success(res) {
    return { type: postConstants.SUBMIT_POST_CREDENTIALS_SUCCESS, payload: res };
  }
  function failure(err) {
    return { type: postConstants.SUBMIT_POST_CREDENTIALS_FAILURE, err };
  }
}
