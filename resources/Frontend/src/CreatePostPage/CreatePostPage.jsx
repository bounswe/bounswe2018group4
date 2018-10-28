import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { alertActions } from '../_actions';


class CreatePostPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            memory: {
                title: '',
                story: '',
                multimedia: '',
                tags: '',
                locations: '',
                time:'',
                password: '',
                decade: ''
            },
            submitted: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const { name, value } = event.target;
        const { memory } = this.state;
        this.setState({
            memory: {
                ...memory,
                [name]: value
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        this.setState({ submitted: true });
        const { memory } = this.state;
        const { dispatch } = this.props;
       
        if (memory.title && memory.story && memory.tags && memory.locations && memory.password && memory.time && memory.decade && memory.multimedia){
            // TODO: add memory
        }
    }



    render() {
        const { registering  } = this.props;
        const { memory, submitted } = this.state;
        return (
            <div className="col-md-6 col-md-offset-3">
                <h2>Create memory</h2>
                Share your memorable experience!
                <p />
                <form name="form" onSubmit={this.handleSubmit}>
                    <div className={'form-group' + (submitted && !memory.title ? ' has-error' : '')}>
                        <label htmlFor="title">Title</label> <br />
                        eg. My summer vacation in Ayvalık
                        <input type="text" className="form-control" name="title" value={memory.title} onChange={this.handleChange} />
                        {submitted && !memory.title &&
                            <div className="help-block">Title is required</div>
                        }
                    </div>
                    <div className={'form-group' + (submitted && !memory.story ? ' has-error' : '')}>
                        <label htmlFor="story">Story</label> <br />
                        What happened?
                        <textarea type="text" className="form-control" name="story"  rows="10" value={memory.story} onChange={this.handleChange} />
                    </div>
                    <div className={'form-group' + (submitted && !memory.multimedia ? ' has-error' : '')}>
                        <label htmlFor="multimedia">Multimedia</label> <br />
                        <input type="file" className="form-control" name="multimedia"  accept="audio/*,image/*,video/*" value={memory.multimedia} onChange={this.handleChange} />
                    </div>
                    <div className={'form-group' + (submitted && !memory.tags ? ' has-error' : '')}>
                        <label htmlFor="tags">Tags</label> <br />
                        Seperate tags by comma (eg. nokia, snake, game)
                        <input type="text" className="form-control" name="tags" value={memory.tags} onChange={this.handleChange} />
                    </div>
                    <div className={'form-group' + (submitted && !memory.locations ? ' has-error' : '')}>
                        <label htmlFor="locations">Locations</label> <br />
                        If your memory includes more than one location, seperate them by comma
                        <input type="text" className="form-control" name="locations" value={memory.locations} onChange={this.handleChange} />
                        {submitted && !memory.locations &&
                            <div className="help-block">At least one location is required</div>
                        }
                    </div>
                    <div className={'form-group' + (submitted && !memory.time ? ' has-error' : '')}>
                        <label htmlFor="time">Time</label> <br />
                        Fill one of the following. <br />
                        1. Exact time
                        <input type="datetime-local" className="form-control" name="time" value={memory.time} onChange={this.handleChange} />
                        {submitted && !memory.time &&
                            <div className="help-block">Time is required</div>
                        }
                        2. Decade <br />
                        <select id="decade" className="form-control"  name="decade" value={memory.decade} onChange={this.handleChange}>
                            <option value="">--</option>
                            <option value="2010">2010s</option>
                            <option value="2000">2000s</option>
                            <option value="1990">1990s</option>
                            <option value="1980">1980s</option>
                            <option value="1970">1970s</option>
                            <option value="1960">1980s</option>
                            <option value="1950">1950s</option>
                            <option value="1940">1940s</option>
                            <option value="1930">1930s</option>
                            <option value="1920">1920s</option>
                            <option value="1910">1910s</option>
                            <option value="1900">1900s</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <button className="btn btn-primary">Share!</button>
                        {registering && 
                            <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                        }
                        <Link to="/" className="btn btn-link">Cancel</Link>
                    </div>
                </form>
            </div>
        );
    }
}


function onlyLettersAndDigits(str) {
    return String(str).match("^(?=.*[a-zA-Z])(?=.*[0-9])");
}
function mapStateToProps(state) {
    const { registering } = state.registration;
    return {
        registering
    };
}

const connectedCreatePostPage = connect(mapStateToProps)(CreatePostPage);
export { connectedCreatePostPage as CreatePostPage };