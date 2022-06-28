import React, {Component} from 'react';
import {Header, HeaderMenuItem, HeaderName, HeaderNavigation, SkipToContent} from "carbon-components-react";

class MyMoodHeader extends Component {
    render() {
        return (
            <Header aria-label="My Mood">
                <SkipToContent />
                <HeaderName href="/" prefix="MyMood.fyi">
                    track and analyse
                </HeaderName>
                <HeaderNavigation aria-label="My Mood">
                    <HeaderMenuItem href="/">Log Mood</HeaderMenuItem>
                    <HeaderMenuItem href="/faq">FAQ</HeaderMenuItem>
                    <HeaderMenuItem href="/analysis">Analysis</HeaderMenuItem>
                </HeaderNavigation>
            </Header>
        );
    }
}

export default MyMoodHeader;