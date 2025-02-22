export function convertX(x, r){
    x = parseFloat(x)
    r = Math.abs(parseInt(r))
    if (r === 0) return 150
    if (x >= 0) {
        return x * 120 / r + 150
    } else {
        return 120 * (1.25 + x / r)
    }
}

export function convertY(y, r){
    y = parseFloat(y)
    r = Math.abs(parseInt(r))
    if (r === 0) return 150
    if (y >= 0) {
        return (y / r - 1.25) * -120
    } else {
        return -120 * y / r + 150
    }
}

export function getDotColor(x, y, r){
    if (checkAreaHit(x, y, r) === true){
        return 'green'
    } else return 'red'
}

function checkAreaHit(x, y, r) {
    x = parseFloat(x)
    y = parseFloat(y)
    r = parseInt(r)
    if (r < 0){
        r = -r
        x = -x;
        y = -y;
    }
    if (x >= 0 && y <= 0 && x <= r / 2 && Math.abs(y) < r){
        return true;
    } else if (x <= 0 && y >= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r/2, 2)){
        return true;
    } else if (x <= 0 && y <= 0 && 2 * y >= -x - r){
        return true;
    } else{
        return false;
    }
}
