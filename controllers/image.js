const Clarifai = require('clarifai');

const app = new Clarifai.App({
    apiKey: '11412dfb88e04deabfa9336c2156367f'
});


const handleApiCall = (req, res) => {
    app.models
        .predict
        (
            {
                id: 'face-detection',
                name: 'face-detection',
                version: '45fb9a671625463fa646c3523a3087d5',
                type: 'visual-detector',
            }, req.body.input
        )
        .then(data => {
            res.json(data);
        })
        .catch(err => res.status(400).json('unable to work with API'))
}

const handleImage = (req, res, db) => {
    const { id } = req.body;
    db('users').where('id', '=', id)
        .increment('entries', 1)
        .returning('entries')
        .then(entries => {
            res.json(entries[0].entries);
        })
        .catch(err => res.status(400).json('unable to get entries'))
}

module.exports = {
    handleImage,
    handleApiCall
};