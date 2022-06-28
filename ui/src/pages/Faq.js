import {Component} from "react";
import {Accordion, AccordionItem} from "@carbon/react";

class Faq extends Component {
    render() {
        return (
            <div className="app">
                {this.props.myMoodHeader}
                <Accordion>
                    <AccordionItem
                        title={<h3>Q: Why is tracking your mood important?</h3>}
                    >
                        A: Tracking your mood is important because...
                    </AccordionItem>
                    <AccordionItem
                        title={<h3>Q: What benefits are there to tracking my mood?</h3>}
                    >
                        A: Benefits such as...
                    </AccordionItem>
                    <AccordionItem
                        title={<h3>Q: What help is available to help process my moods?</h3>}
                    >
                        A: You can find help here...
                    </AccordionItem>
                </Accordion>
            </div>
        )
    }
}

export default Faq