import {Component} from "react";
import {Column, FlexGrid, Row} from "carbon-components-react";
import {Tile} from "@carbon/react/lib/components/Tile/Tile";
import cloneDeep from "lodash.clonedeep";

const initialState = {
    error: null,
    isLoaded: false,
    counts: {
        total: 0,
        positive: 0,
        neutral: 0,
        negative: 0
    }
}

class HeadlineCounts extends Component {
    constructor(props) {
        super(props);
        this.state = cloneDeep(initialState);
    }

    fetchData() {
        this.props.api.getHistoryCountsHeadline()
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
                    <h3>Log counts</h3>
                </Row>
                <Row>
                    <Column>
                        <Tile>Total: <span className="bold">{this.state.counts.total}</span></Tile>
                    </Column>
                    <Column>
                        <Tile>Positive: <span className="greenText bold">{this.state.counts.positive}</span></Tile>
                    </Column>
                    <Column>
                        <Tile>Neutral: <span className="bold">{this.state.counts.neutral}</span></Tile>
                    </Column>
                    <Column>
                        <Tile>Negative: <span className="redText bold">{this.state.counts.negative}</span></Tile>
                    </Column>
                </Row>
            </FlexGrid>
        );
    }
}

export default HeadlineCounts;
