import {Component} from "react";
import cloneDeep from "lodash.clonedeep";
import {FlexGrid, Row} from "carbon-components-react";
import {Column} from "@carbon/react";
import {Bar, BarChart, CartesianGrid, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";

const initialState = {
    error: null,
    isLoaded: false,
    counts: []
}

class EmotionCounts extends Component {
    constructor(props) {
        super(props);
        this.state = cloneDeep(initialState);
    }

    fetchData() {
        this.props.api.getEmotionCountsByDay()
            .then(result => result.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        counts: result
                    })
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    })
                }
            )
    }

    componentDidMount() {
        this.fetchData();
    }

    render() {
        return (
            <FlexGrid>
                <Row>
                    <h3>Gracie AI Emotion detection composition</h3>
                </Row>
                <Row>
                    <Column>
                        <ResponsiveContainer width="100%" height={250}>
                            <BarChart data={this.state.counts}
                                       margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="date" />
                                <YAxis />
                                <Tooltip />
                                <Legend />
                                <Bar dataKey="joy" stackId="a" fill="#F51EEA" />
                                <Bar dataKey="anger" stackId="a" fill="#F10000" />
                                <Bar dataKey="fear" stackId="a" fill="#7700F1" />
                                <Bar dataKey="sadness" stackId="a" fill="#0023F1" />
                                <Bar dataKey="love" stackId="a" fill="#FFC5FC" />
                                <Bar dataKey="surprise" stackId="a" fill="#FFC300" />
                            </BarChart>
                        </ResponsiveContainer>
                    </Column>
                </Row>
            </FlexGrid>
        )
    }
}

export default EmotionCounts;