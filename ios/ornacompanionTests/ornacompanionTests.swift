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

    func testExample() throws {
        let vm = SpecializationViewModel()
        vm.fetchSpecialization()
        let expectation = XCTestExpectation()
        DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 5.0)
    }

}
