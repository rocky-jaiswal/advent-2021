import fs from 'fs'

const fileContents = fs.readFileSync('files/day3.txt').toString().trim()

const binaryArr = fileContents.split(/\n/)
// console.log(binaryArr)
const size = binaryArr[0].length
const positions = Array(size)
  .fill(null)
  .map((_, idx) => idx)

const findBitAtPos = (pos: number) => {
  const atPos = binaryArr.map((binary) => {
    return binary.substr(pos, 1)
  })

  const occurences = atPos.reduce((acc: Record<string, number>, elem: string) => {
    if (acc[elem]) {
      acc[elem] = acc[elem] + 1
    } else {
      acc[elem] = 1
    }
    return acc
  }, {})

  if (occurences['0'] > occurences['1']) {
    return 0
  }
  return 1
}

const positionsMap = positions.map((pos) => {
  return findBitAtPos(pos)
})

const flip = (bin: number) => (bin === 0 ? 1 : 0)

const gammaRate = parseInt(positionsMap.join(''), 2)
const epsilonRate = parseInt(positionsMap.map(flip).join(''), 2)

console.log(gammaRate * epsilonRate)
