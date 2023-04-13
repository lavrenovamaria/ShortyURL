document.getElementById('shorten-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const longUrl = document.getElementById('long-url').value;
    const response = await fetch('/shorten', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ longUrl })
    });

    if (response.ok) {
        const urlMapping = await response.json();
        const resultElement = document.getElementById('result');
        resultElement.innerHTML = `Short URL: <a href="/${urlMapping.shortCode}">/${urlMapping.shortCode}</a>`;
    } else {
        alert('Error: Unable to shorten the URL. Please try again.');
    }
});
