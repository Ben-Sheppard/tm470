import {Component} from "react";
import {CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";
import {FlexGrid, Row} from "carbon-components-react";
import {Column} from "@carbon/react";
import cloneDeep from "lodash.clonedeep";

const initialState = {
    error: null,
    isLoaded: false,
    counts: []
}

class HistoricChart extends Component {
    constructor(props) {
        super(props);
        this.state = cloneDeep(initialState);
    }

    fetchData() {
        this.props.api.getHistoryCountsByDay()
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
                    <h3>Logs vs time</h3>
                </Row>
                <Row>
                    <Column>
                        <ResponsiveContainer width="100%" height={250}>
                            <LineChart data={this.state.counts}
                                       margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="date" />
                                <YAxis />
                                <Tooltip />
                                <Legend />
                                <Line type="monotone" dataKey="positive" stroke="#0BB100" />
                                <Line type="monotone" dataKey="neutral" stroke="#636363" />
                                <Line type="monotone" dataKey="negative" stroke="#C32402" />
                            </LineChart>
                        </ResponsiveContainer>
                    </Column>
                </Row>
            </FlexGrid>
        )
    }
}

export default HistoricChart;