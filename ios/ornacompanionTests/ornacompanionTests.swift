//
//  ornacompanionTests.swift
//  ornacompanionTests
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import XCTest
@testable import ornacompanion

class ornacompanionTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testSingleSpecializationFetch() throws {
        let vm = SpecializationDetailViewModel()
        vm.fetchSpecialization(id: 54)
        let expectation = XCTestExpectation()
        DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 5.0)
    }
    
    func testAllPossibleTiersFetch() throws {
        let vm = SpecializationViewModel()
        vm.fetchSpecializations()
        vm.fetchAllPossibleTiers()
        let expectation = XCTestExpectation()
        DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 5.0)
    }

}
