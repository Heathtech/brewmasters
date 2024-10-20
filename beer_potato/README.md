Intercepts bluetooth HCI data from Tilt beacon broadcasts via [aioblescan](https://github.com/frawau/aioblescan) as JSON.

Periodically pushes the resulting JSON to both the `tilt-webservice` and a local file.

### Requirements:
- Python 3
- Bluetooth (HCI, adapter, driver, etc.)
- [Tilt hydrometer](https://tilthydrometer.com/)

### Running Locally

To run locally, from the `beer_potato` project root:
```bash
python3 read_tilt.py
```