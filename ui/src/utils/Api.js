class Api {
    BASE_URL = "/api"

    async createEntry(entry) {
        return await fetch(this.BASE_URL + '/logs', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(entry)
        });
    }

    getScaleOptions() {
        return fetch(this.BASE_URL + '/scale-options');
    }

    getHistoryCountsHeadline() {
        return fetch(this.BASE_URL + '/logs/history/counts/headline');
    }

    getHistoryCountsByDay() {
        return fetch(this.BASE_URL + '/logs/history/by-day/counts');
    }

    getEmotionCountsByDay() {
        return fetch(this.BASE_URL + '/logs/history/by-day/counts/emotions');
    }
}

export default Api