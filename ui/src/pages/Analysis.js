import {Component} from "react";

class Analysis extends Component {
    render() {
        return (
            <div className="app">
                {this.props.myMoodHeader}
                <h2>Analysis page</h2>
                {this.props.headlineCounts}
                {this.props.historicChart}
                {this.props.emotionCounts}
            </div>
        )
    }
}

export default Analysis