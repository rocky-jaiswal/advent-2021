import fs from 'fs'

const fileContents = fs.readFileSync('files/day3.txt').toString().trim()

const binaryArr = fileContents.split(/\n/)
// console.log(binaryArr)
const size = binaryArr[0].length
const positions = Array(size)
  .fill(null)
  .map((_, idx) => idx)

const findDominantBitAtPos = (pos: number, input = binaryArr) => {
  const atPos = input.map((binary) => {
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

  if (occurences['0'] === occurences['1']) {
    return 1
  }

  if (occurences['0'] > occurences['1']) {
    return 0
  }
  return 1
}

const part1 = () => {
  const positionsMap = positions.map((pos) => {
    return findDominantBitAtPos(pos)
  })

  const flip = (bin: number) => (bin === 0 ? 1 : 0)

  const gammaRate = parseInt(positionsMap.join(''), 2)
  const epsilonRate = parseInt(positionsMap.map(flip).join(''), 2)

  console.log(gammaRate * epsilonRate)
}

part1()

const reduceNums1 = (nums: string[], pos: number) => {
  while (nums.length > 1) {
    const dominantBit = findDominantBitAtPos(pos, nums)
    nums = nums.filter((num) => num[pos] === `${dominantBit}`)
    pos += 1
  }
  return nums
}

const reduceNums2 = (nums: string[], pos: number) => {
  while (nums.length > 1) {
    const dominantBit = findDominantBitAtPos(pos, nums) === 0 ? 1 : 0
    nums = nums.filter((num) => num[pos] === `${dominantBit}`)
    pos += 1
  }
  return nums
}

const part2 = () => {
  const res1 = reduceNums1(binaryArr, 0)
  const res2 = reduceNums2(binaryArr, 0)

  const o2 = parseInt(res1.join(''), 2)
  const co2 = parseInt(res2.join(''), 2)
  console.log(o2 * co2)
}

part2()
