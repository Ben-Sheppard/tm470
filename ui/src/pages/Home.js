import {Component} from "react";
import {
    Button,
    FlexGrid,
    Form,
    FormGroup,
    ProgressIndicator,
    ProgressStep,
    RadioButtonGroup,
    TextArea,
    TextInput,
    ToastNotification
} from "carbon-components-react";
import {Column, Row} from "@carbon/react";
import cloneDeep from "lodash.clonedeep";

const initialState = {
    currentIndex: 0,
    error: null,
    isLoaded: false,
    options: [],
    selectedMoodId: "",
    selectedMoodName: "",
    moodContext: "",
    showNotification: false
}

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = cloneDeep(initialState);
        this.next = this.next.bind(this)
        this.previous = this.previous.bind(this)
        this.submitEntry = this.submitEntry.bind(this)
        this.fetchData = this.fetchData.bind(this)
    }

    fetchData() {
        this.props.api.getScaleOptions()
            .then(result => result.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        options: result
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

    next() {
        this.setState({
            currentIndex: this.state.currentIndex + 1
        });
    }

    previous() {
        this.setState({
            currentIndex: this.state.currentIndex - 1
        });
    }

    storeValue(field, value) {
        this.setState({
            [field]: value
        })
    }

    async submitEntry() {
        await this.props.api.createEntry(
            {moodSelect: this.state.selectedMoodId, moodContext: this.state.moodContext}
        ).then(r => {
            this.setState(initialState);
            this.fetchData();
            this.setState({showNotification: true})
            setTimeout(() => this.setState({showNotification: false}), 5000);
        })
    }

    render() {
        const { error, isLoaded, options } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <div className="app">
                    {this.props.myMoodHeader}
                    <div
                        className="notificationContainer"
                        hidden={this.state.showNotification === false}
                    >
                        <ToastNotification
                            kind="success"
                            title="Success!"
                            subtitle="Log submitted successfully."
                        />
                    </div>
                    <FlexGrid>
                        <Row>
                            <Column key="progressIndicator" sm={1} md={2} lg={4}>
                                <ProgressIndicator vertical currentIndex={this.state.currentIndex}>
                                    <ProgressStep
                                        label="1. Select option"
                                    />
                                    <ProgressStep
                                        label="2. Provide context"
                                    />
                                    <ProgressStep
                                        label="3. Review"
                                    />
                                </ProgressIndicator>
                            </Column>
                            <Column key="moodSelect" sm={3} md={6} lg={12}>
                                <Form>
                                    <FormGroup legendText={<h2>1. How are you feeling?</h2>}
                                               hidden={this.state.currentIndex !== 0}>
                                        <RadioButtonGroup
                                            className="moodSelect"
                                            name="moodSelect"
                                            onChange={event => {
                                                this.storeValue("selectedMoodId", event.target.id);
                                                this.storeValue("selectedMoodName", event.target.value)
                                            }}
                                        >
                                        <FlexGrid>
                                            <Row>
                                            {options.map(option => (
                                                <Column key={option.uuid + 'column'} sm={0} md={1} lg={3}>
                                                <label
                                                    key={option.uuid}
                                                >
                                                    <FlexGrid>
                                                        <Row>
                                                            <span
                                                                className="emoji"
                                                                dangerouslySetInnerHTML={{ __html: option.emojiCode}}
                                                            ></span>
                                                        </Row>
                                                        <Row>
                                                            <input
                                                                type="radio"
                                                                value={option.name}
                                                                key={option.uuid}
                                                                id={option.uuid}
                                                                defaultChecked={this.state.selectedMoodId === option.uuid}
                                                            />
                                                        </Row>
                                                        <Row>
                                                            <span>
                                                                {option.name}
                                                            </span>
                                                        </Row>
                                                    </FlexGrid>
                                                </label>
                                                </Column>
                                            ))}
                                            </Row>
                                        </FlexGrid>
                                        </RadioButtonGroup>
                                        <Button disabled={this.state.selectedMoodId === ""}
                                                onClick={this.next}>Next</Button>
                                    </FormGroup>
                                    <FormGroup legendText={<h2>2. Why are you feeling this way?</h2>}
                                               hidden={this.state.currentIndex !== 1}>
                                        <TextArea
                                            className="moodContext"
                                            name="moodContext"
                                            labelText=""
                                            onChange={event => this.storeValue(event.target.name, event.target.value)}
                                        />
                                        <Button onClick={this.previous}>Previous</Button>
                                        <Button disabled={this.state.moodContext === ""}
                                                onClick={this.next}>Next</Button>
                                    </FormGroup>
                                    <FormGroup legendText={<h2>Review</h2>} hidden={this.state.currentIndex !== 2}>
                                        <div
                                            className="reviewSection"
                                        >
                                        <TextInput
                                            id="selectedMood"
                                            labelText="Mood selected"
                                            value={this.state.selectedMoodName}
                                            readOnly
                                        />
                                        <br />
                                        <TextArea
                                            labelText="Mood context"
                                            value={this.state.moodContext}
                                            readOnly
                                        />
                                        </div>
                                        <Button onClick={this.previous}>Previous</Button>
                                        <Button onClick={this.submitEntry}>Submit</Button>
                                    </FormGroup>
                                </Form>
                            </Column>
                        </Row>
                    </FlexGrid>
                </div>
            );
        }
    }
}

export default Home;